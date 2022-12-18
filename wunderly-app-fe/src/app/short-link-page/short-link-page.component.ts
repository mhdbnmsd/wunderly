import {Component} from '@angular/core';
import {Link} from "./Link";
import {LinkService} from "../link.service";

@Component({
  selector: 'app-short-link-page',
  templateUrl: './short-link-page.component.html',
  styleUrls: ['./short-link-page.component.css']
})
export class ShortLinkPageComponent {

  constructor(private linkService: LinkService) {
  }

  model = new Link("", "", "");

  submitted = false;

  onSubmit() {
    this.submitted = true;
    this.linkService.postLink(this.model).subscribe(response => {
      this.model = response;
    });
  }
}
