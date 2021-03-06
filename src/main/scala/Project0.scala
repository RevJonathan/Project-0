import java.sql.{Connection, DriverManager}
import scala.io.StdIn.readLine

object Project0 {

  def main(args: Array[String]): Unit = {
    val loggedIn = true
    println("Hello! Welcome to the transaction App!")
    val username = readLine("Please enter your username: ")
    val password = readLine("Please enter your password: ")
    val accessLevel = username
    while(loggedIn.equals(true)){
      if((!username.equals("Admin") || !password.equals("root")) &&
        (!username.equals("user") || !password.equals("user"))){
        println("Incorrect login credentials!")
        return
      }
      if(accessLevel.equals("Admin")){
        println("Welcome " + username + ", what would you like to do? ")
        println("Press:\n 1. Look up client by name\n 2. View list of Clients' name and ID \n 3. Add a new client"+
        "\n 4. To exit")
        val adminRes = readLine()
        if(adminRes.equals("1")) {
          println("Here is the list of Clients' name:")
          getClientsName()
        } else if(adminRes.equals("2")){
          println("Here is the list of clients' ID and name:")
          getClientsIDAndName()
        } else if(adminRes.equals("3")){
          addAClient()
        } else {
          println("Thank you have a nice day!")
          return
        }

      } else if(accessLevel.equals("user")){
        println("Welcome " + username + ", what would you like to do?" )
        println("Press:\n 1. View Balance\n 2. View last paid\n 3. Make a deposit\n 4. Make withdraw\n 5. To exit")
        val userRes = readLine()
        if(userRes.equals("1")){
          viewBalance()
        }else if(userRes.equals("2")){
          viewLastPaid()
        }else if(userRes.equals("3")){
          makeDeposit()
        } else if(userRes.equals("4")) {
          makeWithdraw()
        }else {
          println("Have a nice day!")
          return
        }

      }

    }

  }

  def dbConnection (): Connection = {
    val connection ="jdbc:mysql://127.0.0.1:3306/UserInfo"
    val userName = "root"
    val password ="Special7791@"
    val driver = "com.mysql.cj.jdbc.Driver"
    val con = DriverManager.getConnection(connection, userName, password)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(connection, userName, password)
      if(con != null)
        System.out.println("Database connection is successful!")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con

  }

  def getClientsIDAndName() : Connection = {
    val connection ="jdbc:mysql://127.0.0.1:3306/UserInfo"
    val userName = "root"
    val password ="Special7791@"
    val driver = "com.mysql.cj.jdbc.Driver"
    val con = DriverManager.getConnection(connection, userName, password)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(connection, userName, password)
      val query = "SELECT ID , Name FROM ClientInfo"
      val statement = con.createStatement()
      val resultSet = statement.executeQuery(query)
      while(resultSet.next()) {
        val ID = resultSet.getInt("ID")
        val name = resultSet.getString("Name")
        println("ID:" + ID + ", " + name)
      }
      if(con != null)
        System.out.println("")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con
  }

  def getClientsName() : Connection = {
    val connection ="jdbc:mysql://127.0.0.1:3306/UserInfo"
    val userName = "root"
    val password ="Special7791@"
    val driver = "com.mysql.cj.jdbc.Driver"
    val con = DriverManager.getConnection(connection, userName, password)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(connection, userName, password)
      val name = readLine("Please enter a name to look: ")
      val query = "SELECT ID, Name, AcctType FROM ClientInfo WHERE Name = '"+name+"'"
      val statement = con.createStatement()
      val resultSet = statement.executeQuery(query)
      while(resultSet.next()) {
        val id = resultSet.getInt("ID")
        val clientsName = resultSet.getString("Name")
        val acctType = resultSet.getString("AcctType")
        println("ID:" + id + ", " + name + ", " + acctType)
      }
      if(con != null)
        System.out.println("")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con
  }

  def viewBalance() : Connection = {
    val connection ="jdbc:mysql://127.0.0.1:3306/UserInfo"
    val userName = "root"
    val password ="Special7791@"
    val driver = "com.mysql.cj.jdbc.Driver"
    val con = DriverManager.getConnection(connection, userName, password)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(connection, userName, password)
      val acctID = readLine("Please enter your account ID:")
      val query = "SELECT Balance FROM BankInfo WHERE AcctID = '"+acctID+"'"
      val statement = con.createStatement()
      val resultSet = statement.executeQuery(query)
      while(resultSet.next()) {
        println("Your Balance is:")
        val balance = resultSet.getInt("Balance" )
        println("$" + balance)
      }
      if(con != null)
        System.out.println("")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con
  }
  def viewLastPaid() : Connection = {
    val connection ="jdbc:mysql://127.0.0.1:3306/UserInfo"
    val userName = "root"
    val password ="Special7791@"
    val driver = "com.mysql.cj.jdbc.Driver"
    val con = DriverManager.getConnection(connection, userName, password)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(connection, userName, password)
      val acctNum = readLine("Please enter your account number:")
      val query = "SELECT LastPaid FROM BankInfo WHERE acctNum = '"+acctNum+"'"
      val statement = con.createStatement()
      val resultSet = statement.executeQuery(query)
      while(resultSet.next()) {
        val lastPaid = resultSet.getDate("LastPaid")
        println("The last time you were paid was on:\n" +lastPaid)
      }
      if(con != null)
        System.out.println("")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con
  }

  def makeDeposit() : Connection = {
    val connection ="jdbc:mysql://127.0.0.1:3306/UserInfo"
    val userName = "root"
    val password ="Special7791@"
    val driver = "com.mysql.cj.jdbc.Driver"
    val con = DriverManager.getConnection(connection, userName, password)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(connection, userName, password)
      val acctID= readLine("Please enter AcctID: ")
      val makeDeposit = readLine("Please enter amount you would like to deposit in USD: ")
      val query = "UPDATE BankInfo SET Balance = Balance + '"+makeDeposit+"' WHERE AcctID = '"+acctID+"'"
      val balance = "SELECT Balance FROM BankInfo WHERE AcctID = '"+acctID+"'"
      val statement = con.createStatement()
      val resultSet = statement.executeUpdate(query)
      val statement1 = con.createStatement()
      val res = statement.executeQuery(balance)
      while(res.next()) {
        val balance = res.getInt("Balance")
        println("Your new balance is:\n" + "$" + balance)
      }
      if(con != null)
        System.out.println("Deposit successfully completed!")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con
  }
  def makeWithdraw() : Connection = {
    val connection ="jdbc:mysql://127.0.0.1:3306/UserInfo"
    val userName = "root"
    val password ="Special7791@"
    val driver = "com.mysql.cj.jdbc.Driver"
    val con = DriverManager.getConnection(connection, userName, password)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(connection, userName, password)
      val acctID= readLine("Please enter AcctID: ")
      val makeWithdraw = readLine("Please enter amount you would like to withdraw in USD: ")
      val query = "UPDATE BankInfo SET Balance = Balance - '"+makeWithdraw+"' WHERE AcctID = '"+acctID+"'"
      val balance = "SELECT Balance FROM BankInfo WHERE AcctID = '"+acctID+"'"
      val statement = con.createStatement()
      val resultSet = statement.executeUpdate(query)
      val statement1 = con.createStatement()
      val res = statement.executeQuery(balance)
      while(res.next()) {
        val balance = res.getInt("Balance")
        println("Your new balance is:\n" + "$" + balance)
      }
      if(con != null)
        System.out.println("Withdraw successfully completed!")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con
  }

  def addAClient() : Connection = {
    val connection ="jdbc:mysql://127.0.0.1:3306/UserInfo"
    val userName = "root"
    val password ="Special7791@"
    val driver = "com.mysql.cj.jdbc.Driver"
    val con = DriverManager.getConnection(connection, userName, password)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(connection, userName, password)
      val id = readLine("Please assign an ID number for the client: ")
      val name = readLine("Please enter a name for the client: ")
      val acctType = readLine("Please enter account type for client: ")
      val query = "INSERT INTO ClientInfo VALUES('"+id+"','"+name+"','"+acctType+"')"
      val statement = con.createStatement()
      val resultSet = statement.executeUpdate(query)
      if(con != null)
        System.out.println("Successfully added client's information!")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con
  }

}
