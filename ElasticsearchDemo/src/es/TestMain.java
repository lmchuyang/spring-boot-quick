package es;

import java.io.IOException;

import com.esutils.controller.Demo1Crud;

public class TestMain {

	public static void main(String[] args) throws IOException {
		System.out.println("##############################");
		Demo1Crud curd = new Demo1Crud();
		//curd.insert();
		//curd.insert2();
		//curd.query();
		curd.search();
		//curd.search2();
		//curd.filter();
		//curd.delete();
		//curd.deleteSearch();
		//curd.updateInsert();
		System.out.println("*****************************");
	}

}
