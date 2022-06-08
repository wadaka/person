package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Person;

public class PersonDAO {
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;

	private void connect() throws NamingException, SQLException{
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/personapp");
		this.db = ds.getConnection();
	}

	private void disconnect() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
			if(db != null) {
				db.close();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public List<Person> findAll(){
		List<Person> list = new ArrayList<>();
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM persons");
			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				Person person = new Person(id,name,age);
				list.add(person);
			}
		} catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
		return list;
	}

	public void insertOne(Person person) {
		try {
			this.connect();
			ps=db.prepareStatement("INSERT INTO persons(name,age) VALUES(?,?)");
			ps.setString(1, person.getName());
			ps.setInt(2, person.getAge());
			ps.executeUpdate();
			System.out.println(ps);
		} catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
	}

	public void updateOne(Person person) {
		try {
			this.connect();
			ps=db.prepareStatement("UPDATE persons SET name=?,age=? WHERE id=?");
			ps.setString(1, person.getName());
			ps.setInt(2, person.getAge());
			ps.setInt(3, person.getId());
		} catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
	}
}
