package selenium_cucumber.selenium_cucumber.goheavy.login;

import java.io.InputStream;
import java.util.Properties;

import io.cucumber.java.en.*;
import selenium_cucumber.selenium_cucumber.general.Setup;
import selenium_cucumber.selenium_cucumber.general.Steps;
import selenium_cucumber.selenium_cucumber.goheavy.dashboard.DashboardStep;
import selenium_cucumber.selenium_cucumber.goheavy.driver.DriverStep;
import selenium_cucumber.selenium_cucumber.goheavy.fleetowners.FleetStep;

public class LoginStepDefinition {
	private LoginStep loginStep;

	public LoginStepDefinition() {
		loginStep = new LoginStep();

	}

	@Given("The unauthenticated GoHeavy User is in the Login view")
	public void the_unauthenticated_go_heavy_user_is_in_the_view() {
		loginStep.the_unauthenticated_go_heavy_user_is_in_the_view();
	}

	@When("User insert email {string} and password {string}")
	public void user_insert_email_and_password(String string, String string2) {
		loginStep.user_insert_email_and_password(string, string2);
	}

	@When("User clicks on the \"Sign in\" button")
	public void user_clicks_on_the_button() {
		loginStep.user_clicks_on_the_button();
	}

	@Then("The system allows the user access to the system")
	public void the_system_allows_the_user_access_to_the_system() {
		loginStep.the_system_allows_the_user_access_to_the_system();

	}

	@Then("Sytem redirects to {string} view")
	public void sytem_redirects_to_dashboard_view(String string) {
		Steps view = new DashboardStep();
		if ("Drivers List".equalsIgnoreCase(string))
			view = new DriverStep();
		else if ("Fleet Owners List".equalsIgnoreCase(string))
			view = new FleetStep();
		view.checkPage();

	}
	//Scenario #2
	@When("At least one mandatory data {string} or {string} is not inserted")
	public void at_least_one_mandatory_data_is_not_inserted(String email, String password) {
		try {
			loginStep.user_insert_email_and_password(email,password);
		} catch (Exception e) {}
	}

	@Then("The system displays an error messages below each field indicating they are mandatory: {string}")
	public void the_system_displays_an_error_messages_below_each_field_indicating_they_are_mandatory(String message) {
		try {
			loginStep.error_message(message);
		} catch (Exception e) {}
	}

	//Scenario #3
	@When("Inserts an {string} or {string} that is not registered in the system As customer OR does not match")
	public void inserts_an_email_or_pass_that_is_not_registered_in_the_system_As_customer_OR_does_not_match(String email, String password){
		loginStep.user_insert_email_and_password(email,password);
	}

	@Then("The system displays an error messages in a popup window in the upper right side of the view: {string}")
	public void the_system_displays_an_error_messages_in_a_popup_window_in_the_upper_right_side_of_the_view_message(String message){
		try {
			loginStep.invalid_data(message);
		} catch (Exception e) {}
	}
}
