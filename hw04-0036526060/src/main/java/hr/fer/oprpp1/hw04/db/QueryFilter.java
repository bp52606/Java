package hr.fer.oprpp1.hw04.db;

import java.util.List;
import java.util.Iterator;
/**
 * Implementation of a filter for queries
 * 
 * @author 38591
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List of conditional expressions given within a query
	 * 
	 */
	
	private List<ConditionalExpression> list;
	
	/**
	 * Constructor that defines a list of conditional expressions given in a query
	 * 
	 * @param list
	 */
	
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		Iterator<ConditionalExpression> iter = list.iterator();
		boolean allSatisfied = true;
		while(iter.hasNext()) {
			ConditionalExpression current = iter.next();
			if(!current.getComparisonOperator().satisfied(current.getFieldGetter().get(record), 
					current.getStringLiteral())) {
					allSatisfied = false;
					break;
			}
		}
		return allSatisfied;
	}

}
