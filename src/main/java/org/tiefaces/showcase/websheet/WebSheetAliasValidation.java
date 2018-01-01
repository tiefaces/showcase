package org.tiefaces.showcase.websheet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.tiefaces.components.websheet.TieWebSheetBean;
import org.tiefaces.components.websheet.dataobjects.TieCommandAlias;
import org.tiefaces.showcase.websheet.datademo.Employee;

@Named
@ViewScoped
public class WebSheetAliasValidation extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;

	@Override
	public void initialLoad() {

		this.setTieCommandAliasList(prepareAliasList());
		// if submit mode set to true, then the validation only triggered in submit
		// mode.
		this.setTieWebSheetValidationBean(new AliasValidationBean(), true);

		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("employee", new Employee("", 10.25, 1500.0, 0.15, "1974/03/02", "F"));
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/commandalias.xlsx");
		loadWebSheet(stream, context);
	}

	private List<TieCommandAlias> prepareAliasList() {

		List<TieCommandAlias> list = new ArrayList<>();

		String commentForBirthday = "$widget.calendar{showOn=\"button\" pattern=\"yyyy/MM/dd\" readonlyInput=\"true\"}";
		String commentForWorkTime = "$widget.inputnumber{symbol=\" years\" symbolPosition=\"s\" minValue=\"0\" maxValue=\"999\" decimalPlaces=\"2\"}";
		String commentForSex = "$widget.dropdown{itemLabels=\"Male;Female\" itemValues=\"M;F\" }";

		list.add(new TieCommandAlias("${*Date}", commentForBirthday));
		list.add(new TieCommandAlias("${*sex}", commentForSex));
		list.add(new TieCommandAlias("${*worktime}", commentForWorkTime));
		list.add(new TieCommandAlias("${saveTotal}", "$save{employee.total}", true));

		return list;
	}

	@Override
	public void processSave() {
		addMessage("Data Saved");
	}

	@Override
	public void doSubmit() {
		// no need to set submit id if you use normal way calling service for
		// validation.
		increaseSubmitId();
		super.doSubmit();
	}

	@Override
	public void processSubmit() {
		addMessage("Data Submitted with submit id = " + submitId);
	}

	Integer submitId = null;

	private void increaseSubmitId() {
		submitId = (Integer) this.getSerialDataContext().getDataContext().get("submitId");
		if (submitId == null) {
			submitId = 0;
		} else {
			submitId++;
		}
		this.getSerialDataContext().getDataContext().put("submitId", submitId);
	}

	private void addMessage(String summary) {
		Employee employee = (Employee) this.getSerialDataContext().getDataContext().get("employee");
		String detail = "Employee = " + employee;
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
