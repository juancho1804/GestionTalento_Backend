export interface User {
    username: string;
    // password is optional because we avoid storing it when persisting the user
    password?: string;
    role: 'admin' | 'bienestar' | 'capacitacion' | 'incentivos';
    planId?: string;
}