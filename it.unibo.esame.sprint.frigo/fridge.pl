%====================================================================================
% fridge description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxfridge, "localhost",  "MQTT", "0" ).
 qactor( frontend_dummy, ctxfridge, "it.unibo.frontend_dummy.Frontend_dummy").
  qactor( robot_dummy, ctxfridge, "it.unibo.robot_dummy.Robot_dummy").
  qactor( fridge, ctxfridge, "it.unibo.fridge.Fridge").
