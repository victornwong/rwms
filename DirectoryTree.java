package org.victor;

import java.util.*;
import java.io.*;
import java.sql.*;
import groovy.sql.*;
import org.zkoss.zk.ui.*;
import org.zkoss.zul.*;
import org.zkoss.util.*;
import org.victor.*;

public class DirectoryTree
{
	private GuiFuncs guihand;
	private SqlFuncs sqlhand;

	Treechildren tobeshown;
	Sql mainSql;
	
	public DirectoryTree(Treechildren thechild, String iparentid) throws SQLException
	{
		guihand = new GuiFuncs();
		sqlhand = new SqlFuncs();

		mainSql = sqlhand.DMS_Sql();
		if(mainSql == null) return;

		String sqlstatement = "select origid,folderid,folder_desc from folderstructure where deleted=0 and folderparent=" + iparentid;
		ArrayList catlist = (ArrayList)mainSql.rows(sqlstatement);
		tobeshown = thechild;
		fillMyTree(thechild, catlist);
		mainSql.close();
	}

	void fillMyTree(Treechildren tchild, ArrayList prolist) throws SQLException
	{
		for(GroovyRowResult opis : (List<GroovyRowResult>)prolist)
		{
			Treeitem titem = new Treeitem();
			Treerow newrow = new Treerow();

			Treecell newcell1 = new Treecell();
			Treecell newcell2 = new Treecell();
			Treecell newcell3 = new Treecell();

			String thisbranchid = (String)opis.get("origid").toString();
			String folderid = (String)opis.get("folderid");
			if(folderid.length() > 40) folderid = folderid.substring(0,38) + "..";

			String folderdesc = (String)opis.get("folder_desc");
			if(folderdesc.length() > 30) folderdesc = folderdesc.substring(0,28) + "..";

			String sqlqueryline = "select origid,folderid,folder_desc from folderstructure where folderparent=" + thisbranchid;
			ArrayList subchild = (ArrayList)mainSql.rows(sqlqueryline);

			boolean highlite = false;

			if(subchild.size() > 0)
			{
				Treechildren newone = new Treechildren();
				newone.setParent(titem);
				fillMyTree(newone,subchild);
				highlite = true;
				//newcell1.setLabel("${subchild.size()} ${opis[2]}");
			}

			newcell3.setVisible(false);
			newcell3.setLabel(thisbranchid);

			String itmstyle = "font-size:9px";
			//if(highlite) itmstyle += ";background:#99AA88";

			newcell1.setLabel(folderid);
			newcell1.setStyle(itmstyle);
			newcell1.setDraggable("treedrop");

			newcell2.setLabel(folderdesc);
			newcell2.setStyle("font-size:9px");

			newcell1.setParent(newrow);
			newcell2.setParent(newrow);
			newcell3.setParent(newrow);
			newrow.setParent(titem);
			titem.setParent(tchild);
		}
	}

// end of class directoryTree

}

