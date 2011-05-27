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

package org.eclipse.birt.report.engine.api;

import java.io.File;

/**
 * Defines various constants that engine host may need to use
 */
public class EngineConstants {
	public final static String APPCONTEXT_HTML_RENDER_CONTEXT = "HTML_RENDER_CONTEXT"; //$NON-NLS-1$
	public final static String APPCONTEXT_PDF_RENDER_CONTEXT = "PDF_RENDER_CONTEXT"; //$NON-NLS-1$
	public final static String APPCONTEXT_DATASET_CACHE_OPTION = "DATASET_CACHE_OPTION"; //$NON-NLS-1$
	public final static String APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST = "BIRT_VIEWER_HTTPSERVET_REQUEST"; //$NON-NLS-1$
	public final static String APPCONTEXT_CLASSLOADER_KEY = "PARENT_CLASSLOADER"; //$NON-NLS-1$	
	
	//chart and html will all use APPCONTEXT_CHART_RESOLUTION
	public final static String APPCONTEXT_CHART_RESOLUTION = "CHART_RESOLUTION"; //$NON-NLS-1$//false
	
	/**
	 * @deprecated use APPCONTEXT_CHART_RESOLUTION instead of APPCONTEXT_CHART_PRINT_RESOLUTION
	 */
	public final static String APPCONTEXT_CHART_PRINT_RESOLUTION = APPCONTEXT_CHART_RESOLUTION;
	
	/**
	 * This property is a key for putting max row number of extended item in app
	 * context or global variables.
	 */
	public final static String PROPERTY_EXTENDED_ITEM_MAX_ROW = "EXTENDED_ITEM_MAX_ROW"; //$NON-NLS-1$
	
	/**
	 * This property is a key for putting max variable size of extended item in app
	 * context or global variables.
	 */
	public final static String PROPERTY_EXTENDED_ITEM_MAX_VARIABLE_SIZE = "EXTENDED_ITEM_MAX_VARIABLE_SIZE"; //$NON-NLS-1$
	
	//used by ScriptExecutor
	public static final String PROPERTYSEPARATOR = File.pathSeparator;
	public static final String WEBAPP_CLASSPATH_KEY = "webapplication.projectclasspath"; //$NON-NLS-1$
	public static final String WORKSPACE_CLASSPATH_KEY = "workspace.projectclasspath"; //$NON-NLS-1$
	public static final String PROJECT_CLASSPATH_KEY = "user.projectclasspath"; //$NON-NLS-1$
	
	public static final String USER_ACL_KEY = "USER_ACL";
}
