import { NativeEventEmitter, NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-weather-sensor-module' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const WeatherSensorModule = NativeModules.WeatherSensorModule ? NativeModules.WeatherSensorModule  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );
const emitter = new NativeEventEmitter(NativeModules.WeatherSensorModule)


export function set(readingInterval: Uint32Array): void {
  WeatherSensorModule.set(readingInterval)
}

function collectReadings(): void {
  WeatherSensorModule.collectReadings();
}

/**
 * Emits values on the event name "readings"
 * @returns NativeEventEmitter
 */
export function sensorReadings(): NativeEventEmitter {
  collectReadings()
  return emitter
}

export function startSensorReadings(): void {
  WeatherSensorModule.startSensorReadings()
}

export function stopSensorReadings(): void {
  WeatherSensorModule.stopSensorReadings()
}