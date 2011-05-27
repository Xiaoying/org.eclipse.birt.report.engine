/*******************************************************************************
 * Copyright (c) 2008 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.api;


public interface IPostscriptRenderOption extends IRenderOption
{
	public static final String PS_LEVEL = "psLevel";

	public static final String OPTION_PAPER_SIZE = "OptionPagerSize";

	public static final String OPTION_PAPER_TRAY = "OptionPageTray";

	public static final String OPTION_DUPLEX = "OptionDuplex";

	public static final String OPTION_COPIES = "OptionCopies";

	public static final String OPTION_COLLATE = "OptionCollate";

	public static final String OPTION_RESOLUTION = "OptionResolution";

	public static final String OPTION_COLOR = "OptionColor";

	public static final String OPTION_SCALE = "OptionScale";

	public static final String OPTION_AUTO_PAPER_SIZE_SELECTION = "OptionAutoPaperSizeSelection";

	/**
	 * Sets postscript level.
	 * 
	 * @param level
	 */
	void setPostscriptLevel( int level );

	/**
	 * Gets postscript level.
	 */
	int getPostscriptLevel( );

	void setPaperSize( String paperSize );

	String getPaperSize( );

	void setPaperTray( String paperTray );

	String getPaperTray( );

	void setDuplex( String duplex );

	String getDuplex( );

	void setCopies( int copies );

	int getCopies( );

	void setCollate( boolean collate );

	boolean getCollate( );

	void setResolution( String resolution );
	
	String getResolution( );

	void setColor( boolean color );

	boolean getColor( );

	void setScale( int scale );

	int getScale( );

	/**
	 * Select paper size according to page size automatically.
	 * 
	 * @param autoPaperSizeSelection
	 */
	void setAutoPaperSizeSelection( boolean autoPaperSizeSelection );

	boolean getAutoPaperSizeSelection( );
}
