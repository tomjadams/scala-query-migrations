package sqm.migrate

import org.specs._
import _root_.com.novocode.squery.session._

object MigrationRunnerTests extends Specification("Migration runner tests") {

  "A system without a database" can {
    lazy val unmigratedDatabase = new DriverManagerSessionFactory("org.h2.Driver", "jdbc:h2:migrate")

    "create a versioning table by applying a migration" in {
      //response.getResponseCode must be equalTo BadRequest
    }
  }
}
