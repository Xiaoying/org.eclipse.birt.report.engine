/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.layout.pdf.font;

import org.eclipse.birt.report.engine.layout.PDFConstants;

import com.lowagie.text.pdf.BaseFont;

public class FontInfo
{
	private BaseFont bf;

	private float fontSize;

	private int fontStyle;

	private boolean simulation;

	private float lineWidth;
	private float fontHeight;
	private float baselinePosition;
	private float underlinePosition;
	private float linethroughPosition;
	private float overlinePosition;

	public FontInfo( BaseFont bf, float fontSize, int fontStyle,
			boolean simulation )
	{
		this.bf = bf;
		this.fontStyle = fontStyle;
		this.simulation = simulation;
		this.fontSize = fontSize;
		setupFontSize( );
	}

	public FontInfo( FontInfo fontInfo )
	{
		this.bf = fontInfo.bf;
		this.fontStyle = fontInfo.fontStyle;
		this.simulation = fontInfo.simulation;
		this.fontSize = fontInfo.fontSize;
		setupFontSize( );
	}

	public void setFontSize( float fontSize )
	{
		this.fontSize = fontSize;
		setupFontSize( );
	}

	protected void setupFontSize( )
	{

		if ( bf == null )
		{
			lineWidth = 1;
			fontHeight = fontSize;
			baselinePosition = fontSize;
			underlinePosition = fontSize;
			linethroughPosition = fontSize / 2;
			overlinePosition = 0;
			return;
		}

		float ascent = bf.getFontDescriptor( BaseFont.ASCENT, fontSize );
		float descent = bf.getFontDescriptor( BaseFont.DESCENT, fontSize );
		float baseline = bf.getFontDescriptor( BaseFont.UNDERLINE_POSITION,
				fontSize );
		float baseline_thickness = bf.getFontDescriptor(
				BaseFont.UNDERLINE_THICKNESS, fontSize );
		float strike = bf.getFontDescriptor( BaseFont.STRIKETHROUGH_POSITION,
				fontSize );
		float strike_thickness = bf.getFontDescriptor(
				BaseFont.STRIKETHROUGH_THICKNESS, fontSize );

		lineWidth = baseline_thickness;
		if ( lineWidth == 0 )
		{
			lineWidth = strike_thickness;
			if ( lineWidth == 0 )
			{
				lineWidth = fontSize / 20;
			}
		}
		fontHeight = ascent - descent;
		//TODO: the -lineWidth/2 should be move to the draw function
		baselinePosition = ascent - lineWidth / 2;
		underlinePosition = ascent - baseline - lineWidth / 2;
		if ( strike == 0 )
		{
			linethroughPosition = fontHeight / 2 - lineWidth / 2;
		}
		else
		{
			linethroughPosition = ascent - strike - lineWidth / 2;
		}
		//TODO: overline is not same with the HTML, we need change it in future.
		overlinePosition = 0;
	}

	public void setSimulation( boolean simulation )
	{
		this.simulation = simulation;
	}

	public BaseFont getBaseFont( )
	{
		return this.bf;
	}

	public float getFontSize( )
	{
		return this.fontSize;
	}

	public int getFontStyle( )
	{
		return this.fontStyle;
	}

	public boolean getSimulation( )
	{
		return this.simulation;
	}

	public float getLineWidth( )
	{
		return this.lineWidth;
	}

	public int getOverlinePosition( )
	{
		return (int) ( overlinePosition * PDFConstants.LAYOUT_TO_PDF_RATIO );
	}

	public int getUnderlinePosition( )
	{
		return (int) ( underlinePosition * PDFConstants.LAYOUT_TO_PDF_RATIO );
	}

	public int getLineThroughPosition( )
	{
		return (int) ( linethroughPosition * PDFConstants.LAYOUT_TO_PDF_RATIO );
	}

	public int getBaseline( )
	{
		return (int) ( baselinePosition * PDFConstants.LAYOUT_TO_PDF_RATIO );
	}

	/**
	 * Gets the width of the specified word.
	 * 
	 * @param word
	 *            the word
	 * @return the points of the width
	 */
	public float getWordWidth( String word )
	{
		if ( word == null )
		{
			return 0;
		}
		if ( bf == null )
		{
			return word.length( ) * ( fontSize / 2 );
		}
		// FIXME the width should consider the italic/bold font style.
		return bf.getWidthPoint( word, fontSize );
	}

	/**
	 * Gets the height of the specified word.
	 * 
	 * @return the height of the font, it equals ascent+|descent|+leading
	 */
	public float getWordHeight( )
	{
		return fontHeight;
	}

	public String getFontName( )
	{
		assert bf != null;
		String[][] familyFontNames = bf.getFamilyFontName( );
		String[] family = familyFontNames[familyFontNames.length - 1];
		return family[family.length - 1];
	}
}