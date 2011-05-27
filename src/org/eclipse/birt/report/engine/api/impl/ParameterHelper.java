/*******************************************************************************
 * Copyright (c)2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.data.engine.api.IBinding;
import org.eclipse.birt.data.engine.api.IResultIterator;
import org.eclipse.birt.data.engine.api.ISortDefinition;
import org.eclipse.birt.data.engine.api.querydefn.Binding;
import org.eclipse.birt.data.engine.api.querydefn.QueryDefinition;
import org.eclipse.birt.data.engine.api.querydefn.ScriptExpression;
import org.eclipse.birt.data.engine.api.querydefn.SortDefinition;
import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.report.data.adapter.api.IModelAdapter;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.ReportParameterConverter;
import org.eclipse.birt.report.engine.api.impl.GetParameterDefinitionTask.CascadingParameterSelectionChoice;
import org.eclipse.birt.report.model.api.AbstractScalarParameterHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.elements.AbstractScalarParameter;

import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;


public class ParameterHelper
{
	private static final String VALUE_PREFIX = "__VALUE__";

	private static final String LABEL_PREFIX = "__LABEL__";

	private boolean distinct;
	private Comparator comparator;
	private String labelColumnName;
	private String valueColumnName;
	private String valueType;
	private boolean fixedOrder;
	private boolean alreadySorted;
	private ReportParameterConverter converter;
	
	private int parameterType;
	
	static final int SCALAR_PARAMETER = 1;
	static final int FILTER_PARAMETER = 2;

	private String pattern = null;

	public ParameterHelper( AbstractScalarParameterHandle param,
			ULocale ulocale, TimeZone timezone )
	{
		this.labelColumnName = getLabelColumnName( param );
		this.valueColumnName = getValueColumnName( param );
		this.valueType = param.getDataType( );
		this.alreadySorted = param.getSortByColumn( ) != null;
		this.distinct = param.distinct( );
		
		String sortDirection = param.getSortDirection( );
		boolean sortByLabel = "label".equalsIgnoreCase( param.getSortBy( ) );
		if ( param instanceof ScalarParameterHandle )
		{
			parameterType = SCALAR_PARAMETER;
			ScalarParameterHandle parameter = (ScalarParameterHandle) param;
			this.fixedOrder = parameter.isFixedOrder( );
			if ( param.getLabelExpr( ) == null )
			{ // if no label expression was set, apply pattern to value column
				pattern = parameter.getPattern( );
			}
		}
		else
		{
			parameterType = FILTER_PARAMETER;
		}

		if ( !( parameterType == SCALAR_PARAMETER && fixedOrder )
				&& !alreadySorted && sortDirection != null )
		{
			boolean sortDirectionValue = DesignChoiceConstants.SORT_DIRECTION_ASC.equalsIgnoreCase( sortDirection );
			Comparator choiceComparator = new SelectionChoiceComparator( sortByLabel,
					pattern,
					sortDirectionValue,
					ulocale );
			this.comparator = new DistinctComparatorDecorator( choiceComparator,
					distinct );
		}
		this.converter = new ReportParameterConverter( pattern,
				ulocale,
				timezone );
	}
	
	public CascadingParameterSelectionChoice createCascadingParameterSelectionChoice(
			IResultIterator iterator ) throws BirtException
	{
		String label = getLabel( iterator );
		Object value = getValue( iterator );
		return new CascadingParameterSelectionChoice( label, value );
	}

	public CascadingParameterSelectionChoice createCascadingParameterSelectionChoice(
			IParameterSelectionChoice choice )
	{
		String label = choice.getLabel( );
		Object value = choice.getValue( );
		return new CascadingParameterSelectionChoice( label, value );
	}

	public CascadingParameterSelectionChoice createCascadingParameterSelectionChoice(
			String label, Object value )
	{
		return new CascadingParameterSelectionChoice( label, value );
	}
	
	public Collection createSelectionCollection( )
	{
		if ( ( parameterType == SCALAR_PARAMETER && fixedOrder )
				|| alreadySorted || comparator == null )
		{
			if ( !distinct )
			{
				return new ArrayList( );
			}
			return new ArrayList( )
			{
				private static final long serialVersionUID = 1L;
				private Set values = new HashSet( );
				public boolean add( Object arg0 )
				{
					if ( !values.contains( arg0 ) )
					{
						values.add( arg0 );
						return super.add( arg0 );
					}
					return false;
				}
			};
		}
		return new TreeSet( comparator );
	}
	
	public String getLabel( IResultIterator resultIterator )
			throws BirtException
	{
		if ( labelColumnName == null && pattern == null )
		{
			return null;
		}
		else
		{
			String name = labelColumnName != null ? labelColumnName
					: valueColumnName;
			Object value = resultIterator.getValue( name );
			return converter.format( value );
		}
	}
	
	public Object getValue( IResultIterator resultIterator )
			throws BirtException
	{
		Object value = resultIterator.getValue( valueColumnName );
		return EngineTask.convertParameterType( value, valueType );
	}

	public static void addParameterBinding( QueryDefinition queryDefn,
			AbstractScalarParameterHandle parameter, IModelAdapter adapter )
			throws DataException
	{
		String labelColumnName = getLabelColumnName( parameter );
		String valueColumnName = getValueColumnName( parameter );
		if ( labelColumnName != null )
		{
			org.eclipse.birt.report.model.api.Expression mexpr = (org.eclipse.birt.report.model.api.Expression) parameter
					.getExpressionProperty(
							AbstractScalarParameter.LABEL_EXPR_PROP )
					.getValue( );
			ScriptExpression dexpr = adapter.adaptExpression( mexpr );
			addBinding( queryDefn, labelColumnName, dexpr );
		}
		org.eclipse.birt.report.model.api.Expression mexpr = (org.eclipse.birt.report.model.api.Expression) parameter
				.getExpressionProperty( AbstractScalarParameter.VALUE_EXPR_PROP )
				.getValue( );

		String dataType = parameter.getDataType( );
		if ( DesignChoiceConstants.PARAM_TYPE_STRING.equals( dataType ) )
		{
			dataType = DesignChoiceConstants.PARAM_TYPE_JAVA_OBJECT;
		}
		ScriptExpression dexpr = adapter.adaptExpression( mexpr, dataType );
		addBinding( queryDefn, valueColumnName, dexpr );
	}

	public static void addBinding( QueryDefinition queryDefinition,
			String columnName, ScriptExpression expr ) throws DataException
	{
		IBinding binding = new Binding( columnName, expr );
		queryDefinition.addBinding( binding );
	}

	public static void addParameterSortBy( QueryDefinition queryDefn,
			AbstractScalarParameterHandle parameter, IModelAdapter adapter )
	{
		String sortBy = parameter.getSortByColumn( );
		if ( sortBy != null )
		{
			String sortDirection = parameter.getSortDirection( );
			if ( sortDirection != null )
			{
				org.eclipse.birt.report.model.api.Expression mexpr = (org.eclipse.birt.report.model.api.Expression) parameter.getExpressionProperty( AbstractScalarParameter.SORT_BY_COLUMN_PROP )
						.getValue( );
				ScriptExpression dexpr = adapter.adaptExpression( mexpr );
				SortDefinition sort = new SortDefinition( );
				sort.setExpression( dexpr );
				boolean direction = DesignChoiceConstants.SORT_DIRECTION_ASC.equalsIgnoreCase( sortDirection );
				sort.setSortDirection( direction ? ISortDefinition.SORT_ASC
						: ISortDefinition.SORT_DESC );
				queryDefn.addSort( sort );
			}
		}
	}

	static class DistinctComparatorDecorator implements Comparator
	{
		private boolean distinct;
		private Comparator comparator;
		
		public DistinctComparatorDecorator( Comparator comparator, boolean distinct )
		{
			this.comparator = comparator;
			this.distinct = distinct;
		}
		
		public int compare( Object obj1, Object obj2 )
		{
			int result = comparator.compare( obj1, obj2 );
			if ( result == 0 && !distinct)
			{
				result = 1;
			}
			return result;
		}
	}
	
	public static String getValueColumnName( AbstractScalarParameterHandle parameter )
	{
		return VALUE_PREFIX + "_" + parameter.getName( );
	}

	public static String getLabelColumnName(  AbstractScalarParameterHandle parameter )
	{
		if ( parameter.getLabelExpr( ) == null )
		{
			return null;
		}
		return LABEL_PREFIX + "_" + parameter.getName( );
	}
}

