import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { AppRoot } from './app/app-root';
import { routes } from './app/app.routes';

bootstrapApplication(AppRoot, {
  providers: [ provideRouter(routes) ]
}).catch(err => console.error(err));
