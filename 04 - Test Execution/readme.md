# Test Execution

## Content

- [Coverage](#coverage)
- [Maven Surfire Plugin](#maven-surfire-plugin)
- [Maven FailSafe Plugin](#Maven-failsafe-plugin)
- [Surfire Test Reports](#surfire-test-reports)
- [](#)
- [](#)

## Coverage

IntelliJ allows to easily calculate the test coverage of a particular test 
with right-click on it and running it with "Run 'TestName' with Coverage"
or of all the tests with right-click on java test folder "Run 'All Tests' with Coverage".

After the coverage has benn calculated it is possible to navigate through the coverage of the 
different packages, methods and classes in the Coverage windows.
In each class it is possible to see green and red bars beside the line number that indicate
if the line is covered by a test or not.


---

## Maven Surfire Plugin

The Maven Surfire Plugin is a plugin used during the `test` maven phase 
of the build cycle to generate test results reports for Unit Tests as xml or plain text
under `target/surfire-reports`.

**Important**: to run the tests during the maven phase it is necessary to include `unit-jupiter-engine` dependency.

---

## Maven FailSafe Plugin

The Maven FailSafe Plugin has the same scope as Maven Surfire Plugin
but its target are the Integration Tests.
The reports can be found under `target/failssafe-reports`.

**Important**: to run the tests during the maven phase it is necessary to include `unit-jupiter-engine` dependency.

**Important**: If it is a multi module project the failsafe plugin dependency 
must be included in the module pom, if it is in the parent it will not work.

---

## Surfire Test Reports

It is possible to create a readable report (html) from the surfire reports.

To do so it is needed to add a plugin to the reporting pom section:
```xml
<reporting>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-report-plugin</artifactId>
            <version>${maven.plugin.surfire.version}</version>
        </plugin>
    </plugins>
</reporting>
```

Since there is a conflict with this version it is also needed to add to the build plugins
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-site-plugin</artifactId>
    <version>${maven-site-plugin.version}</version>
</plugin>
```

Now it is possible to tun the maven phase `site` where a html site is created will all the documentation.
The site can be found under `target/site/index.html`

At the section `Project Reports` it is possible to find `Surfire Report` with all the
information about the tests executions.

---

##


---

##


