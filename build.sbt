name := "temporary"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq("com.google.apis" % "google-api-services-calendar" % "v3-rev376-1.25.0","com.google.oauth-client" % "google-oauth-client-jetty" % "1.11.0-beta",
  "com.google.api-client" % "google-api-client" % "1.23.0", "javax.mail" % "mail" % "1.4.1", "org.mnode.ical4j" % "ical4j" % "1.0.2"
)
