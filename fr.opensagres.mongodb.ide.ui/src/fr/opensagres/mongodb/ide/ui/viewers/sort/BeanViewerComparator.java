package fr.opensagres.mongodb.ide.ui.viewers.sort;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class BeanViewerComparator extends ViewerComparator  {

	/** property name used to sort **/
	private String sortPropertyName;
	/** the sort direction **/
	private int sortDirection;

	private int compare(Object o1, Object o2) {
		if (sortPropertyName == null) {
			return 0;
		}
		if ((o1 instanceof Comparable) && (o2 instanceof Comparable)) {
			// Compare simple type like String, Integer etc
			Comparable c1 = ((Comparable) o1);
			Comparable c2 = ((Comparable) o2);
			return compare(c1, c2);
		}

		o1 = BeanUtils.getValue(o1, sortPropertyName);
		o2 = BeanUtils.getValue(o2, sortPropertyName);
		if ((o1 instanceof Comparable) && (o2 instanceof Comparable)) {
			// Compare simple type like String, Integer etc
			Comparable c1 = ((Comparable) o1);
			Comparable c2 = ((Comparable) o2);
			return compare(c1, c2);
		}
		return 0;
	}

	private int compare(Comparable c1, Comparable c2) {
		if (sortDirection == SWT.UP) {
			return c2.compareTo(c1);
		}
		return c1.compareTo(c2);
	}

	public String getSortPropertyName() {
		return sortPropertyName;
	}

	public void setSortPropertyName(String sortPropertyName) {
		this.sortPropertyName = sortPropertyName;
	}

	public int getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(int sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	@Override
	public void sort(final Viewer viewer, Object[] elements) {
        Arrays.sort(elements, new Comparator() {
            public int compare(Object a, Object b) {
                return BeanViewerComparator.this.compare(a, b);
            }
        });
    }	
	
	
}
