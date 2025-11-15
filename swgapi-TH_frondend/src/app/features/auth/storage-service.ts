import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  
    isStorageAvailable(): boolean {
    try {
      const testKey = '__storage_test__';
      window.localStorage.setItem(testKey, testKey);
      window.localStorage.removeItem(testKey);
      return true;
    } catch {
      return false;
    }
  }

  setItem(key: string, value: string): void {
    if (this.isStorageAvailable()) {
      window.localStorage.setItem(key, value);
    }
  }

  getItem(key: string): string | null {
    if (this.isStorageAvailable()) {
      return window.localStorage.getItem(key);
    }
    return null;
  }

  removeItem(key: string): void {
    if (this.isStorageAvailable()) {
      window.localStorage.removeItem(key);
    }
  }
}
