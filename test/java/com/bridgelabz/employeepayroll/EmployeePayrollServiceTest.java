package com.bridgelabz.employeepayroll;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.employeepayroll.EmployeePayrollService.IOService;

public class EmployeePayrollServiceTest {
	@Test
	public void given3EmployeeWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmps = { new EmployeePayrollData(1, "Jeff Bezos", 100000.0),
				new EmployeePayrollData(2, "Bill Gates", 200000.0),
				new EmployeePayrollData(3, "Mark Zuckerberg", 300000.0),
				new EmployeePayrollData(4, "Sunder Pichai", 400000.0) };
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		Assert.assertEquals(4, entries);
	}

	@Test
	public void givenFileOnReadingFileShouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> entries = employeePayrollService.readPayrollData(IOService.FILE_IO);
	}

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		System.out.println(employeePayrollData);
		Assert.assertEquals(4, employeePayrollData.size());
	}

	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDatabase() throws PayrollSystemException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(); // created employeePayrollService
																						// object
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO); // reading
																															// data
																															// from
																															// EmployeePayrollData
		employeePayrollService.updateEmployeeSalary("terisa", 3000000.0);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("terisa");
		Assert.assertTrue(result);
	}

	@Test
	public void givenNewSalaryForEmployee_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB()
			throws PayrollSystemException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("terisa", 3000000.0);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("terisa");
		Assert.assertTrue(result);
	}

	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService
				.readEmployeePayrollForDateRange(IOService.DB_IO, startDate, endDate);
		Assert.assertEquals(4, employeePayrollData.size());
	}

	@Test
	public void findSumAverageMinMaxCount_ofEmployees_ShouldMatchEmployeeCount() throws PayrollSystemException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> genderToAverageSalaryMap = employeePayrollService.getAvgSalary(IOService.DB_IO);
		Double avgSalaryMale = 50000.0;
		Assert.assertEquals(avgSalaryMale, genderToAverageSalaryMap.get("M"));
		Double avgSalaryFemale = 3000000.0;
		Assert.assertEquals(avgSalaryFemale, genderToAverageSalaryMap.get("F"));
	}

	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() throws PayrollSystemException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.addEmployeePayrollData("Charles", 50000.0, LocalDate.now(), "M");
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Charles");
		Assert.assertTrue(result);
	}
}
