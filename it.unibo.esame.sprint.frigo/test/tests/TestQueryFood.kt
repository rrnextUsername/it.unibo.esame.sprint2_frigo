package tests

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.coroutines.GlobalScope
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch
import it.unibo.kactor.sysUtil
import org.junit.jupiter.api.AfterEach
import it.unibo.kactor.MsgUtil
  
class TestQueryFood {
  var resource : ActorBasic? = null
  var robotDummy : ActorBasic? = null
	
	@BeforeEach
	fun systemSetUp() {
  	 		GlobalScope.launch{ //activate an observer ...
 				itunibo.coap.observer.main()		//blocking
 			}	
  	 		GlobalScope.launch{
 			    println(" %%%%%%% TestFridge starts fridge mind ")
				it.unibo.ctxFridge.main()
 			}
			delay(5000)		//give the time to start
			resource = sysUtil.getActor("fridge")	
			robotDummy = sysUtil.getActor("robot_dummy")	
		    println(" %%%%%%% TestFridge getActors resource=${resource}")
 	}
 
	@AfterEach
	fun terminate() {
		println(" %%%%%%% TestFridge terminate ")
	}
 
	@Test
	fun queryFoodTest() {
		println(" %%%%%%% TestFridge  queryFoodTest with food present")		
		sendQueryMessage(robotDummy!!,"torta",1000)
		solveCheckGoal(robotDummy!!,"result(present)")
		
		
		println(" %%%%%%% TestFridge  queryFoodTest with food absent")		
		sendQueryMessage(robotDummy!!,"pasta",1000)
		solveCheckGoal(robotDummy!!,"result(absent)")
 	}
	
//----------------------------------------
	
	fun sendQueryMessage( actor : ActorBasic,name: String, time : Long ){
			println("--- sendQueryMessage queryPresence(cibo,$name)")
		actor.scope.launch{
			actor.forward("queryPresence", "queryPresence(cibo,$name)" ,"fridge" ) 
 		}
		delay(time) //give time to do the move
  	}
	
	fun solveCheckGoal( actor : ActorBasic, goal : String){
		actor.solve( goal  )
		var result =  actor.resVar
		assertTrue(result=="success","%%%%%%% TestFridge expected 'success' found $result")
	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}
