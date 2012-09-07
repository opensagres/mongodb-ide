/**
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com> and Pascal Leclercq <pascal.leclercq@gmail.com>
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.opensagres.nosql.ide.mongodb.ui.singlesourcing;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Utilities for RCP/RAP Single Sourcing.
 * 
 * 
 */
public class SingleSourcingUtils {

	private static final String paintBordersFor = "paintBordersFor";
	private static final String FormToolkit_paintBordersFor = FormToolkit.class
			.getName() + "_" + paintBordersFor;
	private static final String setAutoUpload = "setAutoUpload";
	//private static final String FileDialog_setAutoUpload = FileDialog.class
	//		.getSimpleName() + "_" + setAutoUpload;

	private static Map<String, Boolean> methodsExists = new HashMap<String, Boolean>();

/**
	 * Invoke {@link FormToolkit#paintBordersFor(Composite) using reflection because in RAP context, this method doesn't exist. 
	 * 
	 * @param toolkit
	 * @param parent
	 *            the parent that owns the children for which the border needs
	 *            to be painted.
	 */
	public static void FormToolkit_paintBordersFor(FormToolkit toolkit,
			Composite parent) {
		// the method FormToolkit#paintBordersFor does not exist in the RAP
		// implementation.
		// invoke it using reflection.
		try {
			if (!isMethodExists(FormToolkit_paintBordersFor)) {
				// RAP context, the FormToolkit#paintBordersFor doesn't exist.
				return;
			}
			Method method = toolkit.getClass().getMethod(paintBordersFor,
					Composite.class);
			setMethodExists(FormToolkit_paintBordersFor, true);
			// RCP context, the FormToolkit#paintBordersFor exists.
			method.invoke(toolkit, parent);
		} catch (Exception e) {
			setMethodExists(FormToolkit_paintBordersFor, false);
		}
	}

/**
	 * Invoke {@link FileDialog#setAutoUpload(boolean) using reflection because in RCP context, this method doesn't exist.
	 * @param dialog
	 * @param autoUpload
	 */
//	public static void FileDialog_setAutoUpload(FileDialog dialog,
//			boolean autoUpload) {
//		// the method FileDialog#setAutoUpload does not exist in the RCP
//		// implementation.
//		// invoke it using reflection.
//		try {
//			if (!isMethodExists(FileDialog_setAutoUpload)) {
//				// RCP context, the FileDialog#setAutoUpload doesn't exist.
//				return;
//			}
//			Method method = dialog.getClass().getMethod(setAutoUpload,
//					boolean.class);
//			setMethodExists(FileDialog_setAutoUpload, true);
//			// RAP context, the FileDialog_setAutoUpload exists.
//			method.invoke(dialog, autoUpload);
//		} catch (Exception e) {
//			setMethodExists(FileDialog_setAutoUpload, false);
//		}
//	}

	/**
	 * Set the given method name as exists or not.
	 * 
	 * @param methodName
	 * @param exists
	 */
	private static void setMethodExists(String methodName, boolean exists) {
		methodsExists.put(methodName, exists);
	}

	/**
	 * Returns true if the method name exists and false otherwise.
	 * 
	 * @param methodName
	 * @return
	 */
	private static boolean isMethodExists(String methodName) {
		Boolean exists = methodsExists.get(methodName);
		if (exists != null && exists == false) {
			return false;
		}
		return true;
	}

}
