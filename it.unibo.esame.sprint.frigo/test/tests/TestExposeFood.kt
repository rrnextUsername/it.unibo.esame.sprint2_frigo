package tests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.coroutines.GlobalScope
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch
import it.unibo.kactor.sysUtil
import org.junit.jupiter.api.AfterEach
import it.unibo.kactor.MsgUtil

class TestExposeFood {
  var resource : ActorBasic? = null
  var frontEndDummy : ActorBasic? = null
	
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
			frontEndDummy = sysUtil.getActor("frontend_dummy")	
		    println(" %%%%%%% TestFridge getActors resource=${resource}")
 	}
 
	@AfterEach
	fun terminate() {
		println(" %%%%%%% TestFridge terminate ")
	}
 
	@Test
	fun addFoodTest() {
		println(" %%%%%%% TestFridge  exposeFoodTest ")
		emitExposeEvent(frontEndDummy!!,1000)
		solveCheckGoal(frontEndDummy!!,"result( frigo , RESULT )")
 	}
//----------------------------------------
	
	fun emitExposeEvent( actor : ActorBasic, time : Long ){
			println("--- emitEvent exposeFood")
		actor.scope.launch{
			actor.forward("exposeFood", "exposeFood" ,"fridge" ) 
 		}
		delay(time) //give time to do the move
  	}
 	
	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal , "RESULT" )
		var state= actor.getCurSol("RESULT")		
		
		actor.solve( goal )
		var result= actor.resVar
		
		println(" %%%%%%%  actor={$actor.name} goal= $goal  result = $result --- state: $state")
		assertTrue(result=="success","%%%%%%% TestFridge expected 'success' found $result")
	}

	fun delay( time : Long ){
		Thread.sleep( time )
	}


}
