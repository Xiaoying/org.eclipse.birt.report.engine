/*******************************************************************************
 * Copyright (c) 2005, 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.script.internal;

import java.util.logging.Level;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.script.element.IImage;
import org.eclipse.birt.report.engine.api.script.eventhandler.IImageEventHandler;
import org.eclipse.birt.report.engine.api.script.instance.IImageInstance;
import org.eclipse.birt.report.engine.content.IImageContent;
import org.eclipse.birt.report.engine.executor.ExecutionContext;
import org.eclipse.birt.report.engine.ir.ReportItemDesign;
import org.eclipse.birt.report.engine.script.internal.element.Image;
import org.eclipse.birt.report.engine.script.internal.instance.ImageInstance;
import org.eclipse.birt.report.engine.script.internal.instance.RunningState;
import org.eclipse.birt.report.model.api.ImageHandle;

public class ImageScriptExecutor extends ScriptExecutor
{
	public static void handleOnPrepare( ImageHandle imageHandle,
			ExecutionContext context )
	{
		try
		{
			IImage image = new Image( imageHandle );
			IImageEventHandler eh = getEventHandler( imageHandle, context );
			if ( eh != null )
				eh.onPrepare( image, context.getReportContext( ) );
		} catch ( Exception e )
		{
			log.log( Level.WARNING, e.getMessage( ), e );
		}
	}

	public static void handleOnCreate( IImageContent content,
			ExecutionContext context )
	{

		ReportItemDesign imageDesign = (ReportItemDesign) content
				.getGenerateBy( );
		if ( !needOnCreate( imageDesign ) )
		{
			return;
		}
		try
		{
			IImageInstance image = new ImageInstance( content, context,
					RunningState.CREATE );
			if ( handleScript( image, imageDesign.getOnCreate( ), context )
					.didRun( ) )
				return;
			IImageEventHandler eh = getEventHandler( imageDesign, context );
			if ( eh != null )
				eh.onCreate( image, context.getReportContext( ) );
		}
		catch ( Exception e )
		{
			addException( context, e, imageDesign.getHandle( ) );
		}
	}

	public static void handleOnRender( IImageContent content,
			ExecutionContext context )
	{
		ReportItemDesign imageDesign = (ReportItemDesign) content
				.getGenerateBy( );
		if ( !needOnRender( imageDesign ) )
		{
			return;
		}
		try
		{
			IImageInstance image = new ImageInstance( content, context,
					RunningState.RENDER );
			if ( handleScript( image, imageDesign.getOnRender( ), context )
					.didRun( ) )
				return;
			IImageEventHandler eh = getEventHandler( imageDesign, context );
			if ( eh != null )
				eh.onRender( image, context.getReportContext( ) );
		}
		catch ( Exception e )
		{
			addException( context, e, imageDesign.getHandle( ) );
		}
	}

	public static void handleOnPageBreak( IImageContent content,
			ExecutionContext context )
	{
		ReportItemDesign imageDesign = (ReportItemDesign) content
				.getGenerateBy( );
		if ( !needOnPageBreak( imageDesign ) )
		{
			return;
		}
		try
		{
			IImageInstance image = new ImageInstance( content, context,
					RunningState.PAGEBREAK );
			if ( handleScript( image, imageDesign.getOnPageBreak( ), context )
					.didRun( ) )
				return;
			IImageEventHandler eh = getEventHandler( imageDesign, context );
			if ( eh != null )
				eh.onPageBreak( image, context.getReportContext( ) );
		}
		catch ( Exception e )
		{
			addException( context, e, imageDesign.getHandle( ) );
		}
	}

	private static IImageEventHandler getEventHandler( ReportItemDesign design,
			ExecutionContext context )
	{
		try
		{
			return (IImageEventHandler) getInstance( design, context );
		}
		catch ( ClassCastException e )
		{
			addClassCastException( context, e, design.getHandle( ),
					IImageEventHandler.class );
		}
		catch ( EngineException e )
		{
			addException( context, e, design.getHandle( ) );
		}
		return null;
	}

	private static IImageEventHandler getEventHandler( ImageHandle handle,
			ExecutionContext context )
	{
		try
		{
			return (IImageEventHandler) getInstance( handle, context );
		}
		catch ( ClassCastException e )
		{
			addClassCastException( context, e, handle, IImageEventHandler.class );
		}
		catch ( EngineException e )
		{
			addException( context, e, handle );
		}
		return null;
	}
}
