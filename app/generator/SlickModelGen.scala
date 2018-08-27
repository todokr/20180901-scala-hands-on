package generator

import slick.codegen.SourceCodeGenerator

object SlickModelGen extends App {
  SourceCodeGenerator.run(
    profile = "slick.jdbc.MySQLProfile",
    jdbcDriver = "com.mysql.cj.jdbc.Driver",
    url = "jdbc:mysql://127.0.0.1:3306/play2_hands_on?useSSL=false&nullNamePatternMatchesAll=true",
    outputDir = "./app",
    pkg = "models",
    user = Some("root"),
    password = Some(""),
    ignoreInvalidDefaults = true
  )
}