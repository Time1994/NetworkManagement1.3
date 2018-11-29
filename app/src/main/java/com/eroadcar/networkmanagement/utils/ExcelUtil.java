package com.eroadcar.networkmanagement.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Toast;

import com.eroadcar.networkmanagement.activity.BaseActivity;
import com.eroadcar.networkmanagement.bean.OrderSalesBean;

public class ExcelUtil {
    //内存地址
    public static String root = Environment.getExternalStorageDirectory()
            .getPath();
    public static File file;

    public static void writeExcel(final Context context, List<OrderSalesBean> exportOrder,
                                  String fileName) throws Exception {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && getAvailableStorage() > 1000000) {
            Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
            return;
        }
        String[] title = {"序号", "申请人名称", "申请人性质", "证件类型", "证件号码", "驾驶证发证地", "驾驶证发证单位", "驾驶证号", "出生日期", "国籍"};
//        File dir = new File(context.getExternalFilesDir(null).getPath());
        final File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/eroadcar/");
        file = new File(dir, fileName + ".xls");

        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 创建Excel工作表
        WritableWorkbook wwb;
        OutputStream os = new FileOutputStream(file);
        wwb = Workbook.createWorkbook(os);
        // 添加第一个工作表并设置第一个Sheet的名字
        WritableSheet sheet = wwb.createSheet("征信资料", 0);
        Label label;
        for (int i = 0; i < title.length; i++) {
            // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
            // 在Label对象的子对象中指明单元格的位置和内容
            label = new Label(i, 0, title[i], getHeader());
            // 将定义好的单元格添加到工作表中
            sheet.addCell(label);
        }

        for (int i = 0; i < exportOrder.size(); i++) {
            OrderSalesBean order = exportOrder.get(i);
            String ywtype = "", birth = "", codetype = "";
            if (order.getYfs_sellsq_ywtype().equals("0")) {
                ywtype = "个人";
            } else if (order.getYfs_sellsq_ywtype().equals("1")) {
                ywtype = "单位";
            } else if (order.getYfs_sellsq_ywtype().equals("2")) {
                ywtype = "单位";
            } else if (order.getYfs_sellsq_ywtype().equals("3")) {
                ywtype = "个人";
            }
            if (order.getYfs_sellsq_ywtype().equals("0") ||
                    order.getYfs_sellsq_ywtype().equals("3")) {
                birth = order.getYfs_sellsq_id().substring(6, 14);
            } else {
                birth = "none";
            }

            if (order.getYfs_sellsq_idtype().equals("营业执照")) {
                codetype = "营业执照";
            } else {
                codetype = "身份证";
            }
            Label orderNo = new Label(0, i + 1, (i + 1) + "");
            Label name = new Label(1, i + 1, order.getYfs_sellsq_ownner());
            Label type = new Label(2, i + 1, ywtype);
            Label idtype = new Label(3, i + 1, codetype);
            Label code = new Label(4, i + 1, order.getYfs_sellsq_id());
            Label addr = new Label(5, i + 1, order.getYfs_sellsq_addr().substring(0, 3));
            Label addd = new Label(6, i + 1, order.getYfs_sellsq_addr());
            Label codej = new Label(7, i + 1, order.getYfs_sellsq_id());
            Label bir = new Label(8, i + 1, birth);
            Label guoji = new Label(9, i + 1, "中国");


            sheet.addCell(orderNo);
            sheet.addCell(name);
            sheet.addCell(type);
            sheet.addCell(idtype);
            sheet.addCell(code);
            sheet.addCell(addr);
            sheet.addCell(addd);
            sheet.addCell(codej);
            sheet.addCell(bir);
            sheet.addCell(guoji);

            System.out.println("写入成功");
        }
        // 写入数据
        wwb.write();
        // 关闭文件
        wwb.close();

        ToastUtils.showShort("已保存至文件夹 eroadcar");

//        ((BaseActivity) context).showDialogMessage(context, "已保存至文件夹eroadcar，是否打开文件？", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
        try {
            OpenFileUtils.openFile(context, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//                ((BaseActivity) context).dialogMessage.dismiss();
//
////                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////                intent.addCategory(Intent.CATEGORY_DEFAULT);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//////                intent.setDataAndType(Uri.fromFile(dir), "file/*");
////                intent.setDataAndType(Uri.fromFile(dir), "application/vnd.ms-excel");
//////                intent.addCategory(Intent.CATEGORY_OPENABLE);
////                ((BaseActivity) context).startActivity(intent);
//            }
//        });
    }


    public static WritableCellFormat getHeader() {
        WritableFont font = new WritableFont(WritableFont.TIMES, 10,
                WritableFont.BOLD);// 定义字体
        try {
            font.setColour(Colour.BLUE);// 蓝色字体
        } catch (WriteException e1) {
            e1.printStackTrace();
        }
        WritableCellFormat format = new WritableCellFormat(font);
        try {
            format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
            // format.setBorder(Border.ALL, BorderLineStyle.THIN,
            // Colour.BLACK);// 黑色边框
            // format.setBackground(Colour.YELLOW);// 黄色背景
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 获取SD可用容量
     */
    private static long getAvailableStorage() {

        StatFs statFs = new StatFs(root);
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        long availableSize = blockSize * availableBlocks;
        // Formatter.formatFileSize(context, availableSize);
        return availableSize;
    }
}
