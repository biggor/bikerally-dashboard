package ca.biggor.bikerally.dashboard;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.google.visualization.datasource.DataSourceServlet;
import com.google.visualization.datasource.base.DataSourceException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.query.Query;

@SuppressWarnings("serial")
public class RegistrationDataSourceServlet extends DataSourceServlet {

	@Override
	public DataTable generateDataTable(Query query, HttpServletRequest request)
			throws DataSourceException {
		// Create data table
		DataTable data = new DataTable();
		ArrayList<ColumnDescription> cd = new ArrayList<ColumnDescription>();
		cd.add(new ColumnDescription("Day", ValueType.TEXT, "Day"));
		cd.add(new ColumnDescription("2010", ValueType.NUMBER, "2010"));
		cd.add(new ColumnDescription("2010", ValueType.NUMBER, "2011"));
		cd.add(new ColumnDescription("2010", ValueType.NUMBER, "2012"));
		cd.add(new ColumnDescription("2010", ValueType.NUMBER, "2013"));
		cd.add(new ColumnDescription("2010", ValueType.NUMBER, "2014"));
		cd.add(new ColumnDescription("2010", ValueType.NUMBER, "2015"));
		
		data.addColumns(cd);
		
		//Fill the data table
		data.addRowFromValues("Aug",  87,  43,  51,  76,  22,   1);
        data.addRowFromValues("Sep", 144,  65, 100,  84,  39,  69);
        data.addRowFromValues("Oct", 144,  72, 111,  89,  61,  77);
        data.addRowFromValues("Nov", 144,  90, 140, 107,  79,  84);
        data.addRowFromValues("Dec", 144, 115, 180, 131, 104, 111);
        data.addRowFromValues("Jan", 144, 157, 294, 234, 146, 190);
        data.addRowFromValues("Feb", 144, 199, 319, 256, 250, 234);
        data.addRowFromValues("Mar", 144, 222, 350, 280, 294, 274);
        data.addRowFromValues("Apr", 144, 237, 368, 299, 327, 298);
        data.addRowFromValues("May", 144, 245, 372, 319, 342, 305);
        data.addRowFromValues("Jun", 144, 251, 378, 324, 348, 309);
        data.addRowFromValues("Jul", 144, 254, 388, 331, 355, 314);
		
		return data;
	}

}
