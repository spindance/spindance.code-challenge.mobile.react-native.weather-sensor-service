package com.reactnativeweathersensormodule
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments
import com.facebook.react.modules.core.DeviceEventManagerModule

class WeatherSensorModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private val _reactContext: ReactContext = reactContext

  override fun getName(): String {
    return "WeatherSensorModule"
  }
  
  private fun sendEvent(reactContext: ReactContext, eventName: String, params: String) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
}
  
  val collectScope = CoroutineScope(Dispatchers.Default)
    @ReactMethod
    fun collectReadings() {
        collectScope.launch {
          WeatherSensorService.reader.sensorReaderFlow.collect{value -> sendEvent(_reactContext, "reading", value.toString())}
      }
    }

    // Needed to remove warning
    @ReactMethod
    fun addListener(eventName: String) {}

    // Needed to remove warning
    @ReactMethod
    fun removeListeners(count: Int) {}

    @ReactMethod
    fun set(readingInterval: UInt) {
      WeatherSensorService.reader.set(readingInterval)
    }
    
    @ReactMethod 
    fun startSensorReadings() {
      WeatherSensorService.reader.startSensorReadings()
    }
    
    @ReactMethod
    fun stopSensorReadings() {
      collectScope.cancel()
      WeatherSensorService.reader.stopSensorReadings()
    }
  }
