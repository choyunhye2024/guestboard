package com.numberlist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GuestBoard {

	static Connection con = null;
	static Statement st = null;
	static ResultSet result = null;
	static Scanner sc = new Scanner(System.in);

	public static void run() {

		dbInit();
		loop: while (true) {

			dbPostCount();

			System.out.println("1. 리스트 / 2. 쓰기 / e.종료");
			String cmd = sc.next();
			System.out.println(cmd);
			switch (cmd) {

			case "1":
				// 리스트
				System.out.println("===========방명록리스트===========");
				System.out.println("글번호 / 글쓴이 / 글내용 / 작성시간");
				System.out.println("===============================");

				try {

					result = st.executeQuery("select * from guest");

					while (result.next()) {

						String no = result.getString("g_no");
						String id = result.getString("g_id");
						String content = result.getString("g_board");
						String datetime = result.getString("g_datetime");

						System.out.println(no + "" + id + "" + content + "" + datetime);

					}

				} catch (Exception e) {

					e.printStackTrace();

				}

				break;
			case "2":
				// 쓰기
				sc.nextLine();
				System.out.println("아이디를 입력해주세요:");
				String id = sc.next();
				System.out.println("글 내용을 입력해주세요:");
				String content = sc.nextLine();

				try {

					// insert into guest (g_id, g_datetime, g_board) values ('1',now(),'왔다감');
					String sql = "insert into guest (g_id, g_datetime, g_board)" + "values ('" + id + "',now(),'"
							+ content + "')";

					System.out.println(sql);

					st.executeUpdate(sql);

				} catch (Exception e) {

					e.printStackTrace();
				}

				break;
			case "e":
				// 종료
				System.out.println("종료입니다");
				break loop;

			}

		}

	}

	private static void dbInit() {

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_cat", "root", "root");
			st = con.createStatement();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	private static void dbPostCount() {

		try {
			result = st.executeQuery("select count(*) from guest");
			result.next();
			String count = result.getString("count(*)");

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	private void dbExecuteUpdate(String query) {

		try {

			int resultCount = st.executeUpdate(query);
			System.out.println("처리된 행 수:" + resultCount);

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}
}
