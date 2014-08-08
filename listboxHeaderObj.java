package org.victor;

/*
File: Listbox header object - knock from alsglobal_guifuncs.zs
Written by: Victor Wong
Dated: 13/03/2012

To define those simple listbox header in all modules

*/

public class listboxHeaderObj
{
	public String header_str;
	public boolean header_visible;

	public listboxHeaderObj() {}

	public listboxHeaderObj(String iheaderstr, boolean iheadvisible)
	{
		header_str = iheaderstr;
		header_visible = iheadvisible;
	}
}

