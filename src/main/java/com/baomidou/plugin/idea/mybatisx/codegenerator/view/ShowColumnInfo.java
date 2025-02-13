package com.baomidou.plugin.idea.mybatisx.codegenerator.view;

import com.baomidou.plugin.idea.mybatisx.codegenerator.MysqlUtil;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo.ColumnInfo;
import com.intellij.ui.JBColor;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowColumnInfo extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tableColumn;
    private JTextField fieldPrefixField;
    private JCheckBox allSelect;
    private final ShowTableInfo.FieldConfig fieldConfig;
    // 记录一下修改后的字段,等下一次查看会显示修改后的数据，没有做持久化处理，idea重启后消失修改
    private static final Map<Object, Object> changeFieldMap = new HashMap<>();
    // 修改的行
    private int editRow = -1;
    // 修改的列
    private int editColumn = -1;

    public ShowColumnInfo(String tableName, ShowTableInfo.FieldConfig fieldConfig) {
        this.fieldConfig = fieldConfig;
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        List<ColumnInfo> columnInfoList = MysqlUtil.getInstance().getColumns(tableName);

        // 表格所有行数据
        Object[][] rowData = new Object[columnInfoList.size()][];
        //循环遍历所有行
        for (int i = 0; i < columnInfoList.size(); i++) {
            //每行的列数
            ColumnInfo columnInfo = columnInfoList.get(i);
            Object showField = columnInfo.getColumnName();
            String changeField = (String) changeFieldMap.get(columnInfo.getColumnName());
            if (StringUtils.isNotEmpty(changeField)) {
                showField = changeField;
            }
            Object[] tableInfoArr = {
                true,
                columnInfo.getColumnName(),
                showField,
                columnInfo.getIsNullable(),
                columnInfo.getColumnType(),
                columnInfo.getColumnComment(),
                columnInfo.getColumnKey(),
                columnInfo.getExtra()
            };
            rowData[i] = tableInfoArr;
        }

        String[] columnNames = new String[]{"是否需要导出", "字段名称", "字段重新命名", "是否允许为空", "类型", "备注", "主键", "其他"};
//        Object[][] rowData = {{"1","2","3","4","5"}};
        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                Object o = getValueAt(0, columnIndex);
                return o != null ? o.getClass() : super.getColumnClass(columnIndex);
            }
        };

        // 设置表格内容颜色
        tableColumn.setForeground(JBColor.BLACK);                   // 字体颜色
        tableColumn.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        tableColumn.setSelectionForeground(JBColor.DARK_GRAY);      // 选中后字体颜色
        tableColumn.setSelectionBackground(JBColor.LIGHT_GRAY);     // 选中后字体背景
        tableColumn.setGridColor(JBColor.GRAY);                     // 网格颜色

        // 设置表头
        tableColumn.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        tableColumn.getTableHeader().setForeground(JBColor.RED);                // 设置表头名称字体颜色
        tableColumn.getTableHeader().setResizingAllowed(true);               // 设置不允许手动改变列宽
        tableColumn.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        tableColumn.setPreferredScrollableViewportSize(new Dimension(750, 300));
        tableColumn.setModel(tableModel);
        tableColumn.setAutoCreateRowSorter(true);
        ViewUtil.fitTableColumns(tableColumn);

        tableColumn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 单击确认修改
                if (e.getClickCount() == 1) {
                    if (editRow == -1 || editColumn == -1) {
                        return;
                    }
                    //获得原始FIELD列位置
                    int col = 1;
                    final Object originValue = tableColumn.getValueAt(editRow, col);

                    final Object updateValue = tableColumn.getValueAt(editRow, editColumn);
                    changeFieldMap.put(originValue, updateValue);
                    editRow = -1;
                    editColumn = -1;
                } else if (e.getClickCount() == 2) {
                    // 双击编辑表格
                    int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); //获得行位置
                    int col = ((JTable) e.getSource()).columnAtPoint(e.getPoint()); //获得列位置
                    if (col == 2) { //
                        if (tableColumn.getCellEditor() != null) {
                            tableColumn.getCellEditor().stopCellEditing();
                        }
                        tableColumn.editCellAt(row, col, e);

                        editRow = row;
                        editColumn = col;

                    }
                }
            }
        });

        allSelect.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean s = allSelect.isSelected();
                for (int i = 0; i < tableColumn.getRowCount(); i++) {
                    tableColumn.setValueAt(s, i, 0);
                }
            }
        });
    }

    private void onOK() {
        // add your code here
        for (int i = 0; i < tableColumn.getRowCount(); i++) {
            if ((boolean) tableColumn.getValueAt(i, 0)) {
                fieldConfig.put(((String) tableColumn.getValueAt(i, 1)).toUpperCase(), (String) tableColumn.getValueAt(i, 2));
            }
        }
        fieldConfig.setFieldPrefix(fieldPrefixField.getText());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ShowColumnInfo dialog = new ShowColumnInfo("menu", null);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        dialog.setSize(800, 600);
//        System.exit(0);
    }
}
