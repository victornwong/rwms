package org.victor;
import java.util.*;
import java.text.*;
import java.lang.*;
import groovy.sql.*;
import org.zkoss.zul.*;
import org.zkoss.zk.ui.*;
import java.math.BigDecimal;
//import org.zkoss.zk.ui.*;
//import org.zkoss.zk.zutl.*;
//import org.zkoss.util.media.AMedia;

public class NGfuncs extends GlobalDefs
{
// UI-related funcs

	public final void removeSubDiv(Div idivholder)
	{
		Object[] prvds = idivholder.getChildren().toArray();
		Div myw;
		for(int i=0;i<prvds.length;i++) // remove prev sub-divs if any
		{
			myw = (Div)prvds[i];
			myw.setParent(null);
		}
	}

public final void clearUI_Field(Object[] iob)
{
	Generals kiboo = new Generals();
	for(int i=0; i<iob.length; i++)
	{
		if(iob[i] instanceof Textbox) { Textbox kk = (Textbox)iob[i]; kk.setValue(""); }
		if(iob[i] instanceof Label) { Label kk = (Label)iob[i]; kk.setValue(""); }
		if(iob[i] instanceof Datebox) { Datebox kk = (Datebox)iob[i]; kiboo.setTodayDatebox(kk); }
		if(iob[i] instanceof Listbox) { Listbox kk = (Listbox)iob[i]; kk.setSelectedIndex(0); }
	}
}
/*
public final void disableUI_obj(Object[] iob, boolean iwhat)
{
	for(int i=0; i<iob.length; i++)
	{
		Component kk = (Component)iob[i];
		kk.setDisabled(iwhat);
	}
}
*/
public final Object vMakeWindow(Object ipar, String ititle, String iborder, String ipos, String iw, String ih) throws InterruptedException
{
	Component kk = (Component)ipar;
	Window rwin = new Window(ititle,iborder,true);
	rwin.setWidth(iw); rwin.setHeight(ih);
	rwin.setPosition(ipos); rwin.setParent(kk);
	rwin.setMode("overlapped");
	return rwin;
}

public final void popuListitems_Data2(ArrayList ikb, String[] ifl, GroovyRowResult ir)
{
	for(int i=0; i<ifl.length; i++)
	{
		try {
		Object kk = ir.get(ifl[i]);

		if(kk == null) kk = "";
		else
			if(kk instanceof Date) kk = dtf.format(kk);
		else
			if(kk instanceof Integer) kk = nf0.format(kk);
		else
			if(kk instanceof BigDecimal)
			{
				BigDecimal xt = (BigDecimal)kk;
				BigDecimal rt = xt.remainder(BigDecimal.ONE);
				if(rt.floatValue() != 0.0)
					kk = nf2.format(kk);
				else
					kk = nf0.format(kk);
			}
		else
			if(kk instanceof Double) kk = nf2.format(kk);
		else
			if(kk instanceof Float) kk = kk.toString();
		else
			if(kk instanceof Boolean)
			{
				Boolean mm = (Boolean)kk;
				String wi = (mm) ? "Y" : "N";
				kk = wi;
			}

		ikb.add( kk );
		} catch (Exception e) {}
	}
}

public final void popuListitems_Data(ArrayList ikb, String[] ifl, GroovyRowResult ir)
{
	for(int i=0; i<ifl.length; i++)
	{
		try {
		Object kk = ir.get(ifl[i]);
		if(kk == null) kk = "";
		else
			if(kk instanceof Date) kk = dtf2.format(kk);
		else
			if(kk instanceof Integer) kk = nf0.format(kk);
		else
			if(kk instanceof BigDecimal)
			{
				BigDecimal xt = (BigDecimal)kk;
				BigDecimal rt = xt.remainder(BigDecimal.ONE);
				if(rt.floatValue() != 0.0)
					kk = nf2.format(kk);
				else
					kk = nf0.format(kk);
			}
		else
			if(kk instanceof Double) kk = nf2.format(kk);
		else
			if(kk instanceof Float) kk = kk.toString();
		else
			if(kk instanceof Boolean)
			{
				Boolean mm = (Boolean)kk;
				String wi = (mm) ? "Y" : "N";
				kk = wi;
			}

		ikb.add( kk );
		} catch (Exception e) {}
	}
}

String[] getString_fromUI(Object[] iob)
{
	Generals kiboo = new Generals();
	String[] rdt = new String[iob.length];
	for(int i=0; i<iob.length; i++)
	{
		rdt[i] = "";
		try
		{
			if(iob[i] instanceof Textbox)
			{
				Textbox kk = (Textbox)iob[i];
				rdt[i] = kiboo.replaceSingleQuotes(kk.getValue().trim());
			}

			if(iob[i] instanceof Label)
			{
				Label kk = (Label)iob[i];
				rdt[i] = kiboo.replaceSingleQuotes(kk.getValue().trim());
			}

			if(iob[i] instanceof Listbox)
			{
				Listbox kk = (Listbox)iob[i];
				rdt[i] = kk.getSelectedItem().getLabel();
			}

			if(iob[i] instanceof Datebox)
			{
				Datebox kk = (Datebox)iob[i];
				rdt[i] = dtf2.format( kk.getValue() );
			}

			if(iob[i] instanceof Checkbox)
			{
				Checkbox kk = (Checkbox)iob[i];
				rdt[i] = (kk.isChecked()) ? "1" : "0";
			}
		}
		catch (Exception e) {}
	}
	return rdt;
}

public final void populateUI_Data(Object[] iob, String[] ifl, GroovyRowResult ir)
{
	Generals kiboo = new Generals();
	ListboxHandler lbhand = new ListboxHandler();
	for(int i=0;i<iob.length;i++)
	{
		try
		{
			Object woi = ir.get(ifl[i]);

			if(iob[i] instanceof Textbox || iob[i] instanceof Label)
			{
				String kk = "";
				if(woi == null) kk = "";
				else
				if(woi instanceof Date) kk = dtf2.format(woi);
				else
				if(woi instanceof Integer || woi instanceof Double || woi instanceof BigDecimal) kk = woi.toString();
				else
				if(woi instanceof Float) kk = nf2.format(woi);

				if(iob[i] instanceof Textbox) { Textbox mm = (Textbox)iob[i]; mm.setValue(kk); }
				if(iob[i] instanceof Label) { Label mm = (Label)iob[i]; mm.setValue(kk); }
			}

			if(iob[i] instanceof Checkbox)
			{
				Checkbox kk = (Checkbox)iob[i];
				kk.setChecked( (woi == null ) ? false : (Boolean)woi);
			}

			if(iob[i] instanceof Listbox)
			{
				lbhand.matchListboxItems( (Listbox) iob[i], kiboo.checkNullString((String)woi).toUpperCase() );
			}
			if(iob[i] instanceof Datebox)
			{
				Datebox kk = (Datebox)iob[i];
				kk.setValue( (Date)woi );
			}

		} catch (Exception e) {}
	}
}

// itype: 1=width, 2=height
public final void gpMakeSeparator(int itype, String ival, Component iparent)
{
	Separator sep = new Separator();
	if(itype == 1) sep.setWidth(ival);
	if(itype == 2) sep.setHeight(ival);
	sep.setParent(iparent);
}

public final Button gpMakeButton(Object iparent, String iid, String ilabel, String istyle, Object iclick)
{
	Button retv = new Button();
	if(!istyle.equals("")) retv.setStyle(istyle);
	if(!ilabel.equals("")) retv.setLabel(ilabel);
	if(!iid.equals("")) retv.setId(iid);
	if(iclick != null) retv.addEventListener("onClick", (org.zkoss.zk.ui.event.EventListener)iclick);
	retv.setParent((Component)iparent);
	return retv;
}

public final Label gpMakeLabel(Object iparent, String iid, String ivalue, String istyle)
{
	Label retv = new Label();
	if(!iid.equals("")) retv.setId(iid);
	if(!istyle.equals("")) retv.setStyle(istyle);
	retv.setValue(ivalue);
	retv.setParent((Component)iparent);
	return retv;
}

public final Checkbox gpMakeCheckbox(Object iparent, String iid, String ilabel, String istyle)
{
	Checkbox retv = new Checkbox();
	if(!iid.equals("")) retv.setId(iid);
	if(!istyle.equals("")) retv.setStyle(istyle);
	if(!ilabel.equals("")) retv.setLabel(ilabel);
	retv.setParent((Component)iparent);
	return retv;
}

public final void gpmakeGridHeaderColumns_Width(String[] icols, String[] iwidths, Object iparent)
{
	Columns colms = new Columns();
	for(int i=0; i<icols.length; i++)
	{
		Column hcolm = new Column();
		hcolm.setLabel(icols[i]);
		/*
		Comp asc = new Comp(true,i);
		Comp dsc = new Comp(false,i);
		hcolm.setSortAscending(asc);
		hcolm.setSortDescending(dsc);
		*/
		hcolm.setStyle("font-size:9px");
		hcolm.setWidth(iwidths[i]);
		hcolm.setParent((Component)colms);	
	}
	colms.setParent((Component)iparent);
}

// Non-UI util funcs - move to Generals.java

/*
public final int countMonthYearDiff(int itype, Object ist, Object ied)
{
	//java.util.Calendar std = new GregorianCalendar();
	//java.util.Calendar edd = new GregorianCalendar();
	java.util.Calendar std = java.util.Calendar.getInstance();
	java.util.Calendar edd = java.util.Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	std.setTime( ist.getValue() );
	edd.setTime( ied.getValue() );
	int diffYear = edd.get(java.util.Calendar.YEAR) - std.get(java.util.Calendar.YEAR);
	int diffMonth = diffYear * 12 + edd.get(java.util.Calendar.MONTH) - std.get(java.util.Calendar.MONTH);
	return (itype == 1) ? diffMonth : diffYear;
}
*/


}
