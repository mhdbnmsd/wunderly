import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShortLinkPageComponent } from "./short-link-page/short-link-page.component";
import {StatsPageComponent} from "./stats-page/stats-page.component";


const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: ShortLinkPageComponent},
  {path: 'stats/:key', component: StatsPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
