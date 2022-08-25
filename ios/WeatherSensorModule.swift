import Foundation
import Combine

@available(iOS 13.0, *)
@objc(WeatherSensorModule)
class WeatherSensorModule: RCTEventEmitter {
  private var cancellable : Any?

  public static var shared: WeatherSensorModule?
    override init() {
      super.init()
      WeatherSensorModule.shared = self
    }
        
    override func supportedEvents() -> [String]! {
      return ["reading"]
    }
    
  @available(iOS 13.0, *)
  @objc
  func collectReadings() -> Void {  
    cancellable = WeatherSensorService.shared.reader.sensorReadingsPublisher
      .sink{ value in 
        WeatherSensorModule.shared?.sendEvent(withName: "reading", body: "\(value)")
      }
  }

  @available(iOS 13.0, *)
  @objc
  func set(_ readingInterval: NSInteger) -> Void {
    WeatherSensorService.shared.reader.set(readingInterval: UInt(readingInterval))
  }

  @available(iOS 13.0, *)
  @objc
  func startSensorReadings() -> Void {
    WeatherSensorService.shared.reader.startSensorReadings()
  }

  @available(iOS 13.0, *)
  @objc
  func stopSensorReadings() -> Void {
    WeatherSensorService.shared.reader.stopSensorReadings()
  }
}

