import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

driver = {
    new PhantomJSDriver(new DesiredCapabilities())
    //new ChromeDriver(new DesiredCapabilities())
}