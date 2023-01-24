package com.mycompany.runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import com.mycompany.config.Configure;

@CucumberOptions(features={"src//test//java//com//mycompany//module//test//features"}
,glue={"com/mycompany/stepDefinitions", "com/mycompany/module"}
,plugin = {"pretty", "html:target/cucumber"}
//,tags="@RunThis"
//,tags="@SampleRun"
,tags="@RunThis"
)

//good

public class RunTest extends AbstractTestNGCucumberTests{
	
	@Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
	
}