import { ChangeDetectorRef,Component, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  imports: [],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('inventory-angular');
  private http = inject(HttpClient);
  private changeDetector = inject(ChangeDetectorRef);

  parts:{id: number; name: string; quantity: number}[] = [];
  
  addPart(partName: string, quantity: number){
    const trimmedName = partName.trim();
    if(trimmedName === ''){
      alert('Enter a part name.');
      return;
    }
    if(!Number.isInteger(quantity) || quantity < 0){
      alert('Enter a whole number of 0 or more.');
      return;
    }

    
    this.http
    .post<{name: string; quantity: number }>(
      'http://localhost:8080/api/parts',
      {name: trimmedName, quantity: quantity}
    )
    .subscribe({
      next: () => {
        this.loadParts();
      },
      error: () => {
        alert('Could not add the part.');
      }
    });
  }
  usePart(id: number){
    this.http
    .post<{name: string; quantity: number}>(
      'http://localhost:8080/api/parts/' + id + '/use',
      {}
    )
    .subscribe({
      next: () => {
        this.loadParts();
      },
      error: (error) => {
        if (error.status === 0){
          alert('Cannot reach the backend. Check that it is running.');
        }else if(error.status === 400){
          alert('This part is missing or out of stock. Reload the inventory.');
        }else {
          alert('Could not update stock. Please try again.');
        }
      }
    });
  }
  ngOnInit(){
    this.loadParts();
  }
  loadParts(){
    this.http
    .get<{id: number; name: string; quantity: number }[]>('http://localhost:8080/api/parts')
    .subscribe({
      next: (parts) => {
        this.parts = parts;
        this.changeDetector.markForCheck();
      },
      error: () => {
        alert('Could not load parts. Check that the backend is running.');
      }
    });
  }

}
