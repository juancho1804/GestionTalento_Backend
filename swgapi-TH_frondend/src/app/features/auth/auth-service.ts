import { Injectable } from '@angular/core';
import { User } from './models/user.model';
import { StorageService } from './storage-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUser: User | null = null;
  private loggedIn = false;

  private users: User[] = [
    // NOTE: These hardcoded users are for local/dev/demo only. Replace with a real backend/auth provider.
    { username: 'admin', password: 'admin', role: 'admin' },
    { username: 'bienestar', password: '1234', role: 'bienestar', planId: '2' },
    { username: 'capacitacion', password: '1234', role: 'capacitacion', planId: '1' },
    { username: 'incentivos', password: '1234', role: 'incentivos', planId: '3' },


  ]; 

  constructor(private storageService: StorageService) {
    const userString = this.storageService.getItem('user');
    if (userString) {
      try {
        // stored user should not include password
        this.currentUser = JSON.parse(userString);
        this.loggedIn = true;
      } catch (e) {
        // ignore parse errors and keep loggedOut state
        this.currentUser = null;
        this.loggedIn = false;
      }
    }
  }

  login(username: string, password: string): boolean {
    // Find a matching user by username + password
    const userFound = this.users.find(u => u.username === username && u.password === password);
    if (userFound) {
      // mark as logged in
      this.loggedIn = true;
      // keep full user in memory but persist a safe copy (no password)
      this.currentUser = { ...userFound };
      const safeUser: Partial<User> = { ...userFound };
      delete (safeUser as User).password;
      this.storageService.setItem('user', JSON.stringify(safeUser));
      return true;
    }
    return false;
  }

  logout(): void {
    this.loggedIn = false;
    this.currentUser = null;
    this.storageService.removeItem('user');
  }

  isAuthenticated(): boolean {
    return this.loggedIn;
  }

  getUser(): User | null {
    return this.currentUser;
  }

  getRole(): string | null {
    return this.currentUser ? this.currentUser.role : null;
  }

  getPlanId(): string | undefined {
    return this.currentUser ? this.currentUser.planId : undefined;
  }
  
}
