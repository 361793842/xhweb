package xh.springmvc.handlers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.service.BsStatusService;


@Controller
@RequestMapping(value="/bsstatus")
public class BsStatusController {
	
	@RequestMapping(value="/index/ajax_table2",method=RequestMethod.GET)
	@ResponseBody
	public void selectAllBsStatus(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, List<BsStatusBean>> map = new HashMap<String, List<BsStatusBean>>();
		BsStatusService bsStatusService = new BsStatusService();
		try {
			List<BsStatusBean> list = bsStatusService.selectAllBsStatus();
			map.put("data", list);
			String data = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/ExcelToStationStatus",method=RequestMethod.GET)
	public void ExcelToStationStatus(HttpServletRequest request, HttpServletResponse response){
		try{
			String saveDir =  request.getSession().getServletContext().getRealPath("/upload");
			String pathname=saveDir+"/日常维护-基站.xls";
			File Path=new File(saveDir);
			if(!Path.exists()){Path.mkdirs();}
			File file=new File(pathname);
			WritableWorkbook book=Workbook.createWorkbook(file);			
			WritableFont font=new WritableFont(WritableFont.createFont("微软雅黑"), 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
			WritableCellFormat fontFormat=new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE);   //水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);//垂直居中
			fontFormat.setWrap(true);  //自动换行
			fontFormat.setBackground(Colour.PINK);//背景颜色
			fontFormat.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.DARK_GREEN);
			fontFormat.setOrientation(Orientation.HORIZONTAL);//文字方向

			//设置头部字体格式
            WritableFont font_header = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, 
                    false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
            //应用字体
            WritableCellFormat fontFormat_h = new WritableCellFormat(font_header);
            //设置其他样式
            fontFormat_h.setAlignment(Alignment.CENTRE);//水平对齐
            fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
            fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);//边框
            fontFormat_h.setBackground(Colour.YELLOW);//背景色
            fontFormat_h.setWrap(false);//不自动换行
            

			//设置主题内容字体格式
            WritableFont font_Content = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD, 
                    false, UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
            //应用字体
            WritableCellFormat fontFormat_Content = new WritableCellFormat(font_Content);
            //设置其他样式
            fontFormat_Content.setAlignment(Alignment.CENTRE);//水平对齐
            fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
            fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);//边框
            fontFormat_Content.setBackground(Colour.WHITE);//背景色
            fontFormat_Content.setWrap(false);//不自动换行
            
            //设置数字格式
            jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");    //设置数字格式
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); //设置表单格式    

			Label title=new Label(0,0,"设备信息",fontFormat);


			WritableSheet sheet=book.createSheet("日常维护-基站", 0);
			//sheet.mergeCells(0,0,3,0);

			Label label_1=new Label(0,0,"基站ID",fontFormat_h);//创建单元格
			Label label_2=new Label(1,0,"设备",fontFormat_h);
			Label label_3=new Label(2,0,"基站icp状态",fontFormat_h);
			Label label_4=new Label(3,0,"BSC运行状态",fontFormat_h);
			Label label_5=new Label(4,0,"基站时钟运行状态",fontFormat_h);		
			Label label_6=new Label(5,0,"双工器回波损耗",fontFormat_h);
			Label label_7=new Label(6,0,"BSC运行时间",fontFormat_h);
			Label label_8=new Label(7,0,"BSR运行状态",fontFormat_h);
			Label label_9=new Label(8,0,"主控信道底噪查询",fontFormat_h);
			Label label_10=new Label(9,0,"ENB运行时间（telnet 10.1.基站ID.1)",fontFormat_h);


			
			sheet.setRowView(0,500);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 30);
			sheet.setColumnView(6, 50);
			sheet.setColumnView(7, 20);
			sheet.setColumnView(8, 20);
			sheet.setColumnView(9, 50);
			

			sheet.addCell(label_1);
			sheet.addCell(label_2);
			sheet.addCell(label_3);
			sheet.addCell(label_4);
			sheet.addCell(label_5);
			sheet.addCell(label_6);
			sheet.addCell(label_7);
			sheet.addCell(label_8);
			sheet.addCell(label_9);
			sheet.addCell(label_10);
			List<BsStatusBean> list=BsStatusService.excelToBsStatus();
			for(int i=0;i<list.size();i++){
				BsStatusBean bean=(BsStatusBean)list.get(i);
				int icpS=bean.getStatus();
				int bscS=bean.getStatus();
				String stationClock=bean.getClock_status();
				String dpx1=bean.getReturnloss1();
				String dpx2=bean.getReturnloss2();
				String bscRunTime=bean.getBscRuntime();
				String enbRunTime=bean.getEnbRunTime();
				Label value_1=new Label(0,i+1,String.valueOf(bean.getBsId()),fontFormat_Content);
				Label value_2=new Label(1,i+1,bean.getName(),fontFormat_Content);
				Label value_3=new Label(2,i+1,icpS==1?"NO":"OK",fontFormat_Content);
				Label value_4=new Label(3,i+1,bscS==1?"NO":"OK",fontFormat_Content); //格式化数值
				Label value_5=new Label(4,i+1,stationClock=="" ||stationClock==null ?"NA":stationClock,fontFormat_Content);
				Label value_6=new Label(5,i+1,dpx1==""||dpx1==null && dpx2==""||dpx2==null?"NA":"DPX1="+bean.getReturnloss1()+"dB"+" DPX2="+bean.getReturnloss2()+"dB",fontFormat_Content);
				Label value_7=new Label(6,i+1,bscRunTime=="" ||bscRunTime==null?"NA":bscRunTime,fontFormat_Content);
				Label value_8=new Label(7,i+1,"NA",fontFormat_Content);
				Label value_9=new Label(8,i+1,"NA",fontFormat_Content);
				Label value_10=new Label(9,i+1,enbRunTime==""||enbRunTime==null?"NA":enbRunTime,fontFormat_Content);
				sheet.setRowView(i+1,400);
				sheet.addCell(value_1);
				sheet.addCell(value_2);
				sheet.addCell(value_3);
				sheet.addCell(value_4);
				sheet.addCell(value_5);
				sheet.addCell(value_6);
				sheet.addCell(value_7);
				sheet.addCell(value_8);
				sheet.addCell(value_9);
				sheet.addCell(value_10);
				//System.out.println(assetbean.getNumber());
			}


			book.write();
			book.close(); 
			System.out.print("导出成功");
			DownExcelFile(response,pathname);
		}catch(Exception e){
			e.printStackTrace();

		}
	}
	public void DownExcelFile(HttpServletResponse response,String filePath) throws Exception {
		File file = new File(filePath);
		String fileName="";
		response.setContentType("text/plain;charset=utf-8");
		if (file.exists()) {
			try {   
				// 要用servlet 来打开一个 EXCEL 文档，需要将 response 对象中 header 的 contentType 设置成"application/x-msexcel"。
				response.setContentType("application/x-msexcel");
				// 保存文件名称
				fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
				fileName = new String(fileName.getBytes("GB2312"), "iso8859_1");
				//servlet中，要在 header中设置下载方式
				response.setHeader("Content-Disposition","attachment; filename=" + fileName);
				//缓冲流(BufferedStream)可以一次读写一批数据，,缓冲流(Buffered Stream)大大提高了I/O的性能。
				BufferedInputStream  bis = new BufferedInputStream(new FileInputStream(file));
				//OutputStream输出流
				OutputStream bos = response.getOutputStream();
				byte[] buff = new byte[1024];
				int readCount = 0;
				//每次从文件流中读1024个字节到缓冲里。
				readCount = bis.read(buff);
				while (readCount != -1) {
					//把缓冲里的数据写入浏览器
					bos.write(buff, 0, readCount);
					readCount = bis.read(buff);
				}
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				response.setStatus(HttpServletResponse.SC_OK);
				response.flushBuffer();
				response.getWriter().close();
			} catch (Exception e) {

			}
		}
	}

}
