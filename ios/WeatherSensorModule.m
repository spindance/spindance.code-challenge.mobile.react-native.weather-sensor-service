#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(WeatherSensorModule, RCTEventEmitter)

RCT_EXTERN_METHOD(supportedEvents)
RCT_EXTERN_METHOD(set: (NSInteger *)readingInterval)
RCT_EXTERN_METHOD(startSensorReadings)
RCT_EXTERN_METHOD(stopSensorReadings)
RCT_EXTERN_METHOD(collectReadings)

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

@end
 
