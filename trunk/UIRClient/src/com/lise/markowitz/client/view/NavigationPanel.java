package com.lise.markowitz.client.view;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class NavigationPanel extends VLayout {
	private TreeGrid treeGrid = new TreeGrid();
	
	public NavigationPanel() {
		super();
		treeGrid.setLoadDataOnDemand(false);  
        treeGrid.setWidth(500);  
        treeGrid.setHeight(400);  
        treeGrid.setCanEdit(true);  
        treeGrid.setNodeIcon("icons/16/person.png");  
        treeGrid.setFolderIcon("icons/16/person.png");  
        treeGrid.setAutoFetchData(true);  
        treeGrid.setCanFreezeFields(true);  
        treeGrid.setCanReparentNodes(true);          
  
        TreeGridField nameField = new TreeGridField("Name", 150);  
        nameField.setFrozen(true);  
  
        TreeGridField jobField = new TreeGridField("Job", 150);  
        TreeGridField employeeTypeField = new TreeGridField("EmployeeType", 150);  
        TreeGridField employeeStatusField = new TreeGridField("EmployeeStatus", 150);  
        TreeGridField salaryField = new TreeGridField("Salary");  
        TreeGridField genderField = new TreeGridField("Gender");  
        TreeGridField maritalStatusField = new TreeGridField("MaritalStatus");  
  
        treeGrid.setFields(nameField, jobField, employeeTypeField,employeeStatusField,  
                salaryField, genderField, maritalStatusField);
		addMember(treeGrid);
	}
}
