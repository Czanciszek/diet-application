import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DietListComponent } from './diet-list.component';

describe('DietListComponent', () => {
  let component: DietListComponent;
  let fixture: ComponentFixture<DietListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DietListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DietListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
