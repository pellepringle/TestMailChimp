package Steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

import static org.junit.Assert.assertEquals;


public class ChimpStepdefs {
    private WebDriverWait wait;
    private WebDriver driver;

    @Given("I open {string}")
    public void iOpen(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
            driver = new ChromeDriver(options);
        } else if ((browser.equalsIgnoreCase("edge"))) {
            System.setProperty("webdriver.edge.driver", "C:\\Selenium\\msedgedriver.exe");
            driver = new EdgeDriver();

        }
        driver.get("https://login.mailchimp.com/signup/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Given("I enter {string}")
    public void iEnter(String email) {
        if (email.equals("random")) {
            WebElement emailinput = driver.findElement(By.name("email"));
            emailinput.sendKeys(getrandomEmail());
        } else {
            WebElement emailinput = driver.findElement(By.name("email"));
            emailinput.sendKeys(email);
        }
    }

    @Given("My username is {string} long")
    public void myUsernameIsLong(String username) {
        if (username.equals("120")) {
            WebElement remove = driver.findElement(By.name("username"));
            remove.sendKeys(" ");
            WebElement usernamefield = driver.findElement(By.name("username"));
            usernamefield.clear();
            usernamefield.sendKeys("pellejohansssonpellejohansssonpellejohansssonpellejohansssonpellejohansssonpellejohansssonpellejohansssonpellejohanssson");
        } else if (username.equals("0")) {
            WebElement remove = driver.findElement(By.name("username"));
            remove.sendKeys(" ");
            WebElement usernamefield = driver.findElement(By.name("username"));
            usernamefield.clear();
        }
    }

    @Given("I enter a {string}")
    public void iEnterA(String password) {
        WebElement passwordinput = driver.findElement(By.name("password"));
        passwordinput.sendKeys(password);
    }

    @When("I click sign up")
    public void iClickSignUp() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
        WebElement cookies = driver.findElement(By.id("onetrust-accept-btn-handler"));
        cookies.click();
        WebElement createAccountButton = driver.findElement(By.id("create-account-enabled"));
        createAccountButton.click();
    }

    @Then("We {string} to create account")
    public void weToCreateAccount(String attempt) {
        if (attempt.equals("success")) {
            waitIsDisplayed(By.id("signup-success"));
            String actual = driver.getTitle();
            assertEquals("Success | Mailchimp", actual);

        }
        if (attempt.equals("fail")) {
            boolean actual = waitIsDisplayed(By.cssSelector(".invalid-error"));
            boolean expected = true;
            assertEquals(expected, actual);
            String Errormessage = driver.findElement(By.cssSelector(".invalid-error")).getText();
            System.out.println(Errormessage);
        }
    }

    private String getrandomEmail() {
        String first = "Pelle";
        String last = "Johansson";
        String username;
        Random generator = new Random();
        int randomNumber = generator.nextInt(999999999) + 999999999;
        username = first.charAt(0) + last.substring(0, 7) + randomNumber;
        return username + "@gmail.com";
    }

    private boolean waitIsDisplayed(By by) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element.isDisplayed();
    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
