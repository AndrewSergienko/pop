with Ada.Text_IO, GNAT.Semaphores;
use Ada.Text_IO, GNAT.Semaphores;

with Ada.Containers.Indefinite_Doubly_Linked_Lists;
use Ada.Containers;

procedure Main is
   package String_Lists is new Indefinite_Doubly_Linked_Lists (String);
   use String_Lists;
   
   procedure ProducerConsumer(size, amountElems: Integer) is
      storage: List;
      
      storage_access: Counting_Semaphore(1, Default_Ceiling);
      storage_not_full : Counting_Semaphore(size, Default_Ceiling);
      storage_not_empty : Counting_Semaphore(0, Default_Ceiling);
      
      task type producer is
         entry start(name: String);
      end producer;
      
      task type consumer is
         entry start(name: String);
      end consumer;
      
      task body producer is
         begin
         accept start(name: String) do
            for i in 1..amountElems loop
               storage_not_full.Seize;
               storage_access.Seize;
               storage.Append("Product");
               Put_Line(name & " добавив товар.");
               storage_not_empty.Release;
               storage_access.Release;
               delay 1.0;
            end loop;
         end start;
      end producer;
      
      task body consumer is
         begin
         accept start(name: String) do
            for i in 1..amountElems loop
               storage_not_empty.Seize;
               storage_access.Seize;
               storage.Append("Product");
               Put_Line(name & " взяв товар.");
               storage_not_full.Release;
               storage_access.Release;
               delay 1.0;
            end loop;
         end start;
      end consumer;
      
      producers: array(1..5) of producer;
      consumers: array(1..5) of consumer;
       
   begin
      for i in 1..5 loop
         producers(i).start(name => "Producer" & Integer'Image(i));
         consumers(i).start(name => "Consumer" & Integer'Image(i));
      end loop;
   end ProducerConsumer;
begin
   ProducerConsumer(3, 5);
end Main;
