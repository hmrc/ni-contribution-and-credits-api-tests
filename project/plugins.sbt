resolvers += "HMRC-open-artefacts-maven2" at "https://open.artefacts.tax.service.gov.uk/maven2"
resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
  Resolver.ivyStylePatterns
)

resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("org.scalameta"      % "sbt-scalafmt"       % "2.5.2")
addSbtPlugin("com.timushev.sbt"   % "sbt-updates"        % "0.6.3")
addSbtPlugin("uk.gov.hmrc"        % "sbt-auto-build"     % "3.22.0")
addSbtPlugin("uk.gov.hmrc"        % "sbt-distributables" % "2.5.0")
addSbtPlugin("org.playframework"  % "sbt-plugin"         % "3.0.0")
addSbtPlugin("org.scoverage"      % "sbt-scoverage"      % "2.0.9")

