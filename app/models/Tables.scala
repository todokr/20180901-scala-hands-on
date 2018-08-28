package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Company.schema ++ User.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Company
   *  @param companyName Database column COMPANY_NAME SqlType(VARCHAR), Length(45,true)
   *  @param companyId Database column COMPANY_ID SqlType(BIGINT), AutoInc, PrimaryKey */
  final case class CompanyRow(companyName: String, companyId: Option[Long] = None)
  /** GetResult implicit for fetching CompanyRow objects using plain SQL queries */
  implicit def GetResultCompanyRow(implicit e0: GR[String], e1: GR[Option[Long]]): GR[CompanyRow] = GR{
    prs => import prs._
    val r = (<<?[Long], <<[String])
    import r._
    CompanyRow.tupled((_2, _1)) // putting AutoInc last
  }
  /** Table description of table company. Objects of this class serve as prototypes for rows in queries. */
  class Company(_tableTag: Tag) extends profile.api.Table[CompanyRow](_tableTag, Some("play2_hands_on"), "company") {
    def * = (companyName, Rep.Some(companyId)) <> (CompanyRow.tupled, CompanyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(companyName), Rep.Some(companyId)).shaped.<>({r=>import r._; _1.map(_=> CompanyRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column COMPANY_NAME SqlType(VARCHAR), Length(45,true) */
    val companyName: Rep[String] = column[String]("COMPANY_NAME", O.Length(45,varying=true))
    /** Database column COMPANY_ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val companyId: Rep[Long] = column[Long]("COMPANY_ID", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Company */
  lazy val Company = new TableQuery(tag => new Company(tag))

  /** Entity class storing rows of table User
   *  @param name Database column NAME SqlType(VARCHAR), Length(45,true)
   *  @param email Database column EMAIL SqlType(VARCHAR), Length(255,true)
   *  @param authority Database column AUTHORITY SqlType(VARCHAR), Length(45,true)
   *  @param companyId Database column COMPANY_ID SqlType(BIGINT)
   *  @param userId Database column USER_ID SqlType(BIGINT), AutoInc */
  final case class UserRow(name: String, email: String, authority: String, companyId: Long, userId: Option[Long] = None)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[String], e1: GR[Long], e2: GR[Option[Long]]): GR[UserRow] = GR{
    prs => import prs._
    val r = (<<?[Long], <<[String], <<[String], <<[String], <<[Long])
    import r._
    UserRow.tupled((_2, _3, _4, _5, _1)) // putting AutoInc last
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, Some("play2_hands_on"), "user") {
    def * = (name, email, authority, companyId, Rep.Some(userId)) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(name), Rep.Some(email), Rep.Some(authority), Rep.Some(companyId), Rep.Some(userId)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column NAME SqlType(VARCHAR), Length(45,true) */
    val name: Rep[String] = column[String]("NAME", O.Length(45,varying=true))
    /** Database column EMAIL SqlType(VARCHAR), Length(255,true) */
    val email: Rep[String] = column[String]("EMAIL", O.Length(255,varying=true))
    /** Database column AUTHORITY SqlType(VARCHAR), Length(45,true) */
    val authority: Rep[String] = column[String]("AUTHORITY", O.Length(45,varying=true))
    /** Database column COMPANY_ID SqlType(BIGINT) */
    val companyId: Rep[Long] = column[Long]("COMPANY_ID")
    /** Database column USER_ID SqlType(BIGINT), AutoInc */
    val userId: Rep[Long] = column[Long]("USER_ID", O.AutoInc)

    /** Primary key of User (database name user_PK) */
    val pk = primaryKey("user_PK", (userId, companyId))

    /** Foreign key referencing Company (database name fk_USER_COMPANY) */
    lazy val companyFk = foreignKey("fk_USER_COMPANY", companyId, Company)(r => r.companyId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (userId) (database name USER_ID_UNIQUE) */
    val index1 = index("USER_ID_UNIQUE", userId, unique=true)
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}