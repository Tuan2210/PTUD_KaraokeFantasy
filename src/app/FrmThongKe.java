package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAOCTHD;
import dao.DAOHoaDon;
import dao.DAOMatHang;
import dao.DAOPhong;
import entity.CTHD;
import entity.HoaDon;
import entity.MatHang;
import entity.Phong;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;



public class FrmThongKe extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel pMain;
	private JButton btnTK;
	private JButton btnTongDoanhThu;
	private JButton btnTGHD;
	private LocalDate now;
	private int ngay;
	private int thang;
	private int nam;
	private Date dNow;
	private FixButton btnLamMoi;
	private JDateChooser dateChooserThongKeNgayBatDau;
	private JDateChooser dateChooserThongKeNgayKetThuc;
	private DAOCTHD daoCTHD;
	private DAOMatHang daoMatHang;
	private DAOHoaDon daoHoaDon;
	private DAOPhong daoPhong;
	private JButton btnExcels;
	private DecimalFormat df;
	private DecimalFormat dfs;
	private SimpleDateFormat sf;
	private JPanel pBieuDo;
	private JLabel lblNgayHienTai;

	public Panel getFrmThongKe() {
		return this.pMain;
	}
	@SuppressWarnings("deprecation")
	public FrmThongKe(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {
		

		//connect database
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		daoCTHD = new DAOCTHD();
		daoMatHang = new DAOMatHang();
		daoHoaDon = new DAOHoaDon();
		daoPhong = new DAOPhong();

		getContentPane().setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		getContentPane().add(pMain);
		pMain.setLayout(null);

		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue();
		nam = now.getYear();

		dNow = new Date(nam,thang,ngay);


		JLabel lblHeaderDate = new JLabel("   Th???i gian th???ng k??:");
		lblHeaderDate.setForeground(Color.BLACK);
		lblHeaderDate.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblHeaderDate.setBounds(334, 0, 221, 41);
		pMain.add(lblHeaderDate);

		lblNgayHienTai = new JLabel();
		lblNgayHienTai.setForeground(Color.BLACK);
		lblNgayHienTai.setFont(new Font("SansSerif", Font.ITALIC, 22));
		lblNgayHienTai.setBounds(521, -2, 267, 41);
		pMain.add(lblNgayHienTai);
		
		

		JPanel pThongKe = new JPanel();
		pThongKe.setBackground(new Color(238,239,243,90));
		pThongKe.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pThongKe.setBounds(10, 36, 328, 200);
		pThongKe.setBackground(Color.WHITE);
		pMain.add(pThongKe);
		pThongKe.setLayout(null);

		// Bieu do
		pBieuDo = new JPanel();
		pBieuDo.setBackground(new Color(238,239,243,90));
		pBieuDo.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pBieuDo.setBounds(10, 244, 1243, 374);
		pBieuDo.setBackground(Color.WHITE);
		pMain.add(pBieuDo);
		pBieuDo.setLayout(null);
		

		ButtonGroup bg = new ButtonGroup();
		bg.clearSelection();

		JLabel lblChonNgay = new JLabel("Ng??y b???t ?????u:");
		lblChonNgay.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblChonNgay.setBounds(25, 15, 100, 36);
		pThongKe.add(lblChonNgay);

		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue()-1;
		nam = now.getYear()-1900;
		
		dNow = new Date(nam,thang,ngay);
		
		dateChooserThongKeNgayBatDau = new JDateChooser();
		dateChooserThongKeNgayBatDau.setDateFormatString("dd/MM/yyyy");

		dateChooserThongKeNgayBatDau.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateChooserThongKeNgayBatDau.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		dateChooserThongKeNgayBatDau.getCalendarButton().setBackground(new Color(102, 0, 153));
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserThongKeNgayBatDau.setIcon((ImageIcon) iconCalendar);

		dateChooserThongKeNgayBatDau.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserThongKeNgayBatDau.setBounds(130, 15, 180, 38);
		dateChooserThongKeNgayBatDau.setDate(dNow);
		pThongKe.add(dateChooserThongKeNgayBatDau);


		
		btnTK = new FixButton("Th???ng k??");
		btnTK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTK.setForeground(Color.WHITE);
		btnTK.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTK.setBackground(new Color(114, 23, 153));
		btnTK.setBounds(10, 112, 300, 36);
		Icon iconChart = IconFontSwing.buildIcon(FontAwesome.BAR_CHART, 20, Color.white);
		btnTK.setIcon(iconChart);
		pThongKe.add(btnTK);

		btnLamMoi = new FixButton("L??m m???i");

		btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnLamMoi.setBackground(new Color(114, 23, 153));
		btnLamMoi.setBounds(10, 153, 300, 36);
		Icon iconLamMoi = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.white);
		btnLamMoi.setIcon(iconLamMoi);
		pThongKe.add(btnLamMoi);
		
		btnExcels = new FixButton("Xu???t Excels");
		btnExcels.setBounds(1128, 10, 125, 23);
		pMain.add(btnExcels);
		btnExcels.setForeground(Color.WHITE);
		btnExcels.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnExcels.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnExcels.setBackground(new Color(16, 124, 65));
		Icon iconExcel = IconFontSwing.buildIcon(FontAwesome.FILE_EXCEL_O, 20, Color.white);
		btnExcels.setIcon(iconExcel);
		
		
		dateChooserThongKeNgayKetThuc = new JDateChooser();
		dateChooserThongKeNgayKetThuc.getCalendarButton().setPreferredSize(new Dimension(30, 24));

		dateChooserThongKeNgayKetThuc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserThongKeNgayKetThuc.getCalendarButton().setBackground(new Color(102, 0, 153));
		dateChooserThongKeNgayKetThuc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		Icon iconCalendar1 = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserThongKeNgayKetThuc.setIcon((ImageIcon) iconCalendar1);
		dateChooserThongKeNgayKetThuc.setDateFormatString("dd/MM/yyyy");
		dateChooserThongKeNgayKetThuc.setBounds(130, 60, 180, 38);
		dateChooserThongKeNgayKetThuc.setDate(dNow);
		pThongKe.add(dateChooserThongKeNgayKetThuc);
		
		JLabel lblNgyKtThc = new JLabel("?????n ng??y:");
		lblNgyKtThc.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNgyKtThc.setBounds(25, 62, 100, 36);
		pThongKe.add(lblNgyKtThc);

		JPanel pTongDoanhThu = new JPanel();
		pTongDoanhThu.setBackground(new Color(238,239,243,90));
		pTongDoanhThu.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pTongDoanhThu.setBounds(348, 36, 440, 200);
		pTongDoanhThu.setBackground(Color.WHITE);
		pMain.add(pTongDoanhThu);
		pTongDoanhThu.setLayout(null);

		JLabel lblTDT= new JLabel("  T???ng doanh thu ");
		lblTDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblTDT.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblTDT.setForeground(new Color(148, 0, 211));
		lblTDT.setBounds(107, 172, 226, 17);
		pTongDoanhThu.add(lblTDT);

		btnTongDoanhThu = new FixButton("");

		btnTongDoanhThu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTongDoanhThu.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTongDoanhThu.setForeground(Color.BLACK);
		btnTongDoanhThu.setBackground(Color.WHITE);
		btnTongDoanhThu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTongDoanhThu.setBounds(107, 109, 226, 52);
		pTongDoanhThu.add(btnTongDoanhThu);
		
		JLabel lblDollar = new JLabel("$");
		lblDollar.setFont(new Font("SansSerif", Font.BOLD, 60));
		lblDollar.setHorizontalAlignment(SwingConstants.CENTER);
		lblDollar.setForeground(Color.ORANGE);
		lblDollar.setBounds(107, 11, 226, 87);
		pTongDoanhThu.add(lblDollar);

		JPanel pTgPhongSD = new JPanel();
		pTgPhongSD.setBackground(new Color(238,239,243,90));
		pTgPhongSD.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pTgPhongSD.setBounds(798, 36, 455, 200);
		pTgPhongSD.setBackground(Color.WHITE);
		pMain.add(pTgPhongSD);
		pTgPhongSD.setLayout(null);

		JLabel lblTGSD= new JLabel("Th???i gian ho???t ?????ng ph??ng ");
		lblTGSD.setHorizontalAlignment(SwingConstants.CENTER);
		lblTGSD.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblTGSD.setForeground(new Color(153, 50, 204));
		lblTGSD.setBounds(109, 172, 237, 17);
		pTgPhongSD.add(lblTGSD);

		btnTGHD = new FixButton("");

		btnTGHD.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTGHD.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTGHD.setForeground(Color.BLACK);
		btnTGHD.setBackground(Color.WHITE);
		btnTGHD.setBounds(109, 109, 237, 51);
		pTgPhongSD.add(btnTGHD);
		
		JLabel lblIconTGHoatDong = new JLabel();
		lblIconTGHoatDong.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconTGHoatDong.setBounds(109, 11, 237, 87);
		Icon iconTGHoatDong = IconFontSwing.buildIcon(FontAwesome.MUSIC, 60, Color.red);
		lblIconTGHoatDong.setIcon(iconTGHoatDong);
		pTgPhongSD.add(lblIconTGHoatDong);


		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, -23, 1281, 652);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);
		


		///ActionListener
		btnExcels.addActionListener(this);
		btnTK.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTongDoanhThu.addActionListener(this);
		btnTGHD.addActionListener(this);

		df = new DecimalFormat("###,### VN??");
		dfs = new DecimalFormat("### h");
		sf = new SimpleDateFormat("dd/MM/yyy");
	}
	//s??? ti???n thu??
	public double tinhTienThue(double giaPhong, HoaDon hd) {
		@SuppressWarnings("deprecation")
		int gioVao = hd.getGioVao().getHours(),
				phutVao = hd.getGioVao().getMinutes();
		@SuppressWarnings("deprecation")
		int gioRa = hd.getGioRa().getHours(),
				phutRa = hd.getGioRa().getMinutes();

		int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
		double tongTienThuePhong = 0;
		if(tongThoiGian > 0) {
			if(tongThoiGian <= 60) {
				tongTienThuePhong = giaPhong;
				return tongTienThuePhong;
			}
			else {
				tongTienThuePhong = (tongThoiGian * giaPhong)/60;
				return tongTienThuePhong;
			}
		}

		return -1;
	}
	//tong tien 1 hoa don
	public double tongTienCTHD(double tongTienThue,HoaDon hd) {
		double tong = tongTienThue;

		ArrayList<CTHD> lsCTHD = daoCTHD.getCTHDTheoMaHD(hd.getMaHoaDon());
		for(CTHD ct : lsCTHD) {
			MatHang mh = daoMatHang.getMHTheoMaMH(ct.getMatHang().getMaMatHang());
			tong += mh.getGiaMatHang() * ct.getSoLuong();
		}

		return tong;
	}
	public double tongDoanhThu(ArrayList<HoaDon> lstHD) {
		double doanhThu = 0;
		for(HoaDon hd : lstHD) {
			String phuThu = hd.getPhuThu();
			Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
			double giaPhong =p.getGiaPhong();
			double giaPhuThu = 0;
			if(phuThu.equalsIgnoreCase("Bu???i t???i")) {
				giaPhuThu = 10000;
			}
			if(phuThu.equalsIgnoreCase("Ng??y l???")) {
				giaPhuThu = 30000;
			}
			if(phuThu.equalsIgnoreCase("Cu???i tu???n")) {
				giaPhuThu = 20000;
			}
			giaPhong = giaPhuThu + giaPhong;
			double tongTienThue = tinhTienThue(giaPhong, hd);

			@SuppressWarnings("unused")
			int tongGioThue = (int) ((tongTienThue)/giaPhong);
			@SuppressWarnings("unused")
			int tongPhutThue = (int) (((tongTienThue*60)/giaPhong) % 60);



			double thanhTien = tongTienCTHD(tongTienThue, hd);


			thanhTien = thanhTien - hd.getGiamGia();
			doanhThu+= thanhTien;
		}
		return doanhThu;
	}


	//load th??ng tin th???ng k?? doanh

	//load thong ke doanh thu
	public void loadThongKeDoanhThu() {
		
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		
		@SuppressWarnings("deprecation")
		Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		@SuppressWarnings("deprecation")
		Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		if(ngayBatDau.before(ngayKetThuc)||ngayBatDau.equals(ngayKetThuc)) {
			
			ArrayList<HoaDon> lstHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
			double doanhThu = tongDoanhThu(lstHD);
			btnTongDoanhThu.setText(df.format(doanhThu));
			
		}
		else JOptionPane.showMessageDialog(this, "Ng??y b???t ?????u ph???i nh??? h??n ho???c b???ng ng??y k???t th??c!");
	}

	
	@SuppressWarnings("deprecation")
	public void xuatSoGio(ArrayList<HoaDon> lsHD,String path) throws IOException {
		Workbook workbook = null;
		 
        if (path.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (path.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = workbook.createSheet("DSGio"); 
 
        int rowIndex = 1;
         
        Row title = sheet.createRow(rowIndex);
        
        Cell cellTitle = title.createCell(0,CellType.STRING);
        cellTitle.setCellValue("DANH S??CH S??? GI??? HO???T ?????NG");
        rowIndex++;
        Row headerRow = sheet.createRow(rowIndex);
        Cell cMaPhong = headerRow.createCell(0,CellType.STRING);
        cMaPhong.setCellValue("T??n Ph??ng");
        
        Cell cMaGio = headerRow.createCell(1,CellType.STRING);
        cMaGio.setCellValue("S??? gi???");

        Cell cNgay = headerRow.createCell(2,CellType.STRING);
        cNgay.setCellValue("Ng??y h??t");
        
        rowIndex++; 
   
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();

		Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
		for(int i = 0;i<=noDay;i++) {
			ArrayList<HoaDon> ls = daoHoaDon.getHDtheoNgay(ngayden);
			int count =0;
			String tenP = "";
			for(HoaDon hd :ls) {
				if(hd!=null) {
					tenP = hd.getPhong().getMaPhong().toString();
					int gioVao = hd.getGioVao().getHours(),
							phutVao = hd.getGioVao().getMinutes();
					int gioRa = hd.getGioRa().getHours(),
							phutRa = hd.getGioRa().getMinutes();

					int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
					count += tongThoiGian;
				}
			}	
			Row row = sheet.createRow(rowIndex);
	        Cell cRowMaP = row.createCell(0,CellType.STRING);
	        cRowMaP.setCellValue(tenP);
	        Cell cRowSoGio = row.createCell(1, CellType.NUMERIC);
	        cRowSoGio.setCellValue(dfs.format(count));
	        Cell cNgayHat = row.createCell(2, CellType.NUMERIC);
	        cNgayHat.setCellValue(sf.format(ngayden));
			rowIndex++;
			
			Date ngayMoi = new Date(ngayden.getYear(), ngayden.getMonth(), ngayden.getDate()+1);
			ngayden= ngayMoi;
		}	
        
        File f = new File(path);
         try {
        	
        		 FileOutputStream out = new FileOutputStream(f);
        		 workbook.write(out);
				
        		 out.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "L??u kh??ng th??nh c??ng!\n T??n file ???? t???n t???i");
		}
	}


	//load th???ng k?? s??? gi??? ???? s??? d???ng cho c??c ph??ng
	@SuppressWarnings("deprecation")
	public void loadThongKeSoGio() {
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		

		Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		ArrayList<HoaDon> lstHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
		if(ngayBatDau.before(ngayKetThuc)||ngayBatDau.equals(ngayKetThuc)) {
			int thoiGianThongKe = 0;
			for(HoaDon hd: lstHD) {
				int gioVao = hd.getGioVao().getHours(),
						phutVao = hd.getGioVao().getMinutes();
				int gioRa = hd.getGioRa().getHours(),
						phutRa = hd.getGioRa().getMinutes();

				int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
				thoiGianThongKe += tongThoiGian;
			}
			btnTGHD.setText(dfs.format(thoiGianThongKe)+"");
		}
	}
	public void resetAll() {
		dateChooserThongKeNgayBatDau.setDate(dNow);
		dateChooserThongKeNgayKetThuc.setDate(dNow);
		btnTongDoanhThu.setText("");
		btnTGHD.setText("");
		pBieuDo.removeAll();
		pBieuDo.revalidate();
		pBieuDo.repaint();
	}

	public void addChart() throws RemoteException {
		if(!btnTongDoanhThu.getText().equalsIgnoreCase("")) {
			pBieuDo.removeAll();
			pBieuDo.revalidate();
			pBieuDo.repaint();
			ChartPanel chartPanel = new ChartPanel(createChart());
			chartPanel.setLocation(10, 11);
			chartPanel.setSize(1223, 352);
	        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
	        pBieuDo.add(chartPanel);
	 	}else JOptionPane.showMessageDialog(this, "Vui l??ng th???ng k?? tr?????c khi xem bi???u ?????");
	}
	
	private JFreeChart createChart() throws RemoteException {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Bi???u ????? th???ng k?? doanh thu",
                "Ng??y", "T???ng ti???n",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        
        CategoryAxis domainAxis = plot.getDomainAxis();
		
		  domainAxis.setCategoryLabelPositions(
				  CategoryLabelPositions.UP_45);
		//  CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0) );
        return barChart;
	}
	 
	 @SuppressWarnings({ "unused", "deprecation" })
	public CategoryDataset createDataset() throws RemoteException {
		 final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		 
			java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
			java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();

			Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
			Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
			long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
			for(int i = 0;i<=noDay;i++) {
				ArrayList<HoaDon> ls = daoHoaDon.getHDtheoNgay(ngayden);
				double tongtien =0;
				for(HoaDon hd : ls) {
					if(hd != null) {
						String phuThu = hd.getPhuThu();
						Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
						double giaPhong =p.getGiaPhong();
						double giaPhuThu = 0;
						if(phuThu.equalsIgnoreCase("Bu???i t???i")) {
							giaPhuThu = 10000;
						}
						if(phuThu.equalsIgnoreCase("Ng??y l???")) {
							giaPhuThu = 30000;
						}
						if(phuThu.equalsIgnoreCase("Cu???i tu???n")) {
							giaPhuThu = 20000;
						}
						giaPhong = giaPhuThu + giaPhong;
						double tongTienThue = tinhTienThue(giaPhong, hd);

						int tongGioThue = (int) ((tongTienThue)/giaPhong);
						int tongPhutThue = (int) (((tongTienThue*60)/giaPhong) % 60);

						

						double thanhTien = tongTienCTHD(tongTienThue, hd);


						thanhTien = thanhTien - hd.getGiamGia();
						tongtien+= thanhTien;
					}	
				}
				
				dataset.addValue(tongtien, "S??? ti???n (VN??)", sf.format(ngayden));
				Date ngayMoi = new Date(ngayden.getYear(), ngayden.getMonth(), ngayden.getDate()+1);
				ngayden= ngayMoi;
			}	

	        return dataset;
	}
	 
	
	 
	
	 public void addChartGio() throws RemoteException {
			if(!btnTGHD.getText().equalsIgnoreCase("")) {
				pBieuDo.removeAll();
				pBieuDo.revalidate();
				pBieuDo.repaint();
				ChartPanel chartPanel = new ChartPanel(createChartGio());
				chartPanel.setLocation(10, 11);
				chartPanel.setSize(1223, 352);
		        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		        pBieuDo.add(chartPanel);
		 	}else JOptionPane.showMessageDialog(this, "Vui l??ng th???ng k?? tr?????c khi xem bi???u ?????");
		}
		
		private JFreeChart createChartGio() throws RemoteException {
			  JFreeChart chart = ChartFactory.createPieChart(
		                "Bi???u ????? th???ng k?? gi??? ho???t ?????ng m???i ph??ng",  createDatasetGio(), true, true, true);

	        return chart;
		}
		 
		 @SuppressWarnings("deprecation")
		public PieDataset  createDatasetGio() throws RemoteException {
			 final DefaultPieDataset  dataset = new DefaultPieDataset ();
			
				java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
				java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();

				Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
				Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
				long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
				for(int i = 0;i<=noDay;i++) {
					ArrayList<HoaDon> ls = daoHoaDon.getHDtheoNgay(ngayden);
					int count =0;
					String tenP = "";
					for(HoaDon hd :ls) {
						if(hd!=null) {
							 tenP = hd.getPhong().getMaPhong().toString();
							//Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
							int gioVao = hd.getGioVao().getHours(),
									phutVao = hd.getGioVao().getMinutes();
							int gioRa = hd.getGioRa().getHours(),
									phutRa = hd.getGioRa().getMinutes();

							int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
							count += tongThoiGian;
						}
						}	
					dataset.setValue(tenP, count);
					Date ngayMoi = new Date(ngayden.getYear(), ngayden.getMonth(), ngayden.getDate()+1);
					ngayden= ngayMoi;
				}	

		        return dataset;
		}
		 
			public  void xuatExcel() throws IOException {
				
				
				java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
				java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
				
				@SuppressWarnings("deprecation")
				Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
				@SuppressWarnings("deprecation")
				Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
				ArrayList<HoaDon> lsHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
				FileDialog fileDialog  = new FileDialog(this,"Xu???t h??a ????n ra Excels",FileDialog.SAVE);
				fileDialog.setFile("*.xlsx");
				fileDialog .setVisible(true);
				String name = fileDialog.getFile();
				String fileName = fileDialog.getDirectory() + name;

				if (name == null) {
					return;
				}
				
				if(!fileName.endsWith(".xlsx")||!fileName.endsWith(".xls")) {
					fileName += ".xlsx";
				}
				XuatExcels xuat = new XuatExcels();
				xuat.xuatHoaDon(lsHD, fileName);
				
					
					
			}
public  void xuatExcelsSoGio() throws IOException {
				
				
				java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
				java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
				
				@SuppressWarnings("deprecation")
				Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
				@SuppressWarnings("deprecation")
				Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
				ArrayList<HoaDon> lsHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
				FileDialog fileDialog  = new FileDialog(this,"Xu???t s??? gi??? ra Excels",FileDialog.SAVE);
				fileDialog.setFile("*.xlsx");
				fileDialog .setVisible(true);
				String name = fileDialog.getFile();
				String fileName = fileDialog.getDirectory() + name;

				if (name == null) {
					return;
				}
				
				if(!fileName.endsWith(".xlsx")||!fileName.endsWith(".xls")) {
					fileName += ".xlsx";
				}
				xuatSoGio(lsHD, fileName);
					
					
			}
		 

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnExcels)) {
				try {
					xuatExcel();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					xuatExcelsSoGio();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		if(o.equals(btnTK))
		{
			java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
			java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
			@SuppressWarnings("deprecation")
			Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
			@SuppressWarnings("deprecation")
			Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
			lblNgayHienTai.setText(sf.format(ngayden)+" - "+sf.format(ngayKT));
			
			long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
				if(noDay < 32) {
					loadThongKeDoanhThu();
					loadThongKeSoGio();
				}
				else
			JOptionPane.showMessageDialog(null, "Ch??? ???????c ch???n trong kho???ng th???i gian 1 th??ng");

		}
		if(o.equals(btnLamMoi))
			resetAll();
		if(o.equals(btnTongDoanhThu))
		{
			try {
				addChart();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		if(o.equals(btnTGHD)) {
			try {
				addChartGio();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		}


	}
}


