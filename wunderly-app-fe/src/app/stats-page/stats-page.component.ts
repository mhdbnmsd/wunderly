import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LinkService} from "../link.service";
import {StatsService} from "../stats.service";
import {Stats} from "../short-link-page/Stats";

@Component({
  selector: 'app-stats-page',
  templateUrl: './stats-page.component.html',
  styleUrls: ['./stats-page.component.css']
})
export class StatsPageComponent {

  constructor(private route: ActivatedRoute,
              private router: Router,
              private linkService: LinkService,
              private statsService: StatsService) {}

  url: string = "";

  key: string = "";

  chartOptions: any = {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.key = params.get("key") as string;
      this.linkService.getLink(this.key).subscribe(link => {
          this.url = link.url;
      });
      this.statsService.getStats(this.key).subscribe(stats => {
          this.populateGraph(stats)
      })
    });
  }

  delete() {
    this.linkService.deleteLink(this.key).subscribe(() => {
      this.url = "The url has been deleted";
      setTimeout(() => this.router.navigate(["home"]), 3000);
    })
  }

  private populateGraph(stats :Stats) :void {
    this.chartOptions = {
      title: {
        text: this.url + " visits for the last week"
      },
      animationEnabled: true,
      axisY: {
        includeZero: true
      },
      data: [{
        type: "column",
        indexLabelFontColor: "#5A5757",
        dataPoints: []
      }]
    }
    for (const value in stats.visits) {
      this.chartOptions.data[0].dataPoints.push({label: value, y: stats.visits[value]})
    }
  }
}
