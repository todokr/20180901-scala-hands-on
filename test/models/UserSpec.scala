package models

import org.scalatest._
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class UserSpec extends fixture.FlatSpec with Matchers with AutoRollback {
  val u = User.syntax("u")

  behavior of "User"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = User.find(1L, 1L)
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = User.findBy(sqls.eq(u.companyId, 1L))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = User.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = User.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = User.findAllBy(sqls.eq(u.companyId, 1L))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = User.countBy(sqls.eq(u.companyId, 1L))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = User.create(name = "MyString", email = "MyString", authority = "MyString", companyId = 1L)
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = User.findAll().head
    // TODO modify something
    val modified = entity
    val updated = User.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = User.findAll().head
    val deleted = User.destroy(entity)
    deleted should be(1)
    val shouldBeNone = User.find(1L, 1L)
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = User.findAll()
    entities.foreach(e => User.destroy(e))
    val batchInserted = User.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
