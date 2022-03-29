# cs465-programming-assignment-2

If you want to compile and run my program from command line, follow these steps:
- cd into the root directory of the project
- Run ./gradlew run –args=’infilename.txt’

Example command line execution:

`./gradlew run –-args=’testcase1.txt’`

The specified input file must be located in `src/main/resources` since it will be loaded in as a classpath resource.

An alternative to command line execution would be to load the project into Eclipse & run it from there. To do so:
- Import as a gradle project. File > Import… > Gradle > Existing Gradle Project > Next > > Browse… > select the root directory of the project > Finish
- Right click on the Access.java file in the main package, mouse over Run As, then click Run Configurations…
- In the popup window, click Arguments and specify the file name in the input box labeled “Program arguments:” 
- Click Run

The main method of my program exists in Access.java. 
