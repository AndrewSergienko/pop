with Ada.Text_IO; use Ada.Text_IO;

with GNAT.Semaphores; use GNAT.Semaphores;

with Ada.Containers.Indefinite_Doubly_Linked_Lists;
use Ada.Containers;

procedure Main is
   forks: array(1..5) of Counting_Semaphore(1, Default_Ceiling);
   can_take_fork : Counting_Semaphore(4, Default_Ceiling);

   dinner_nums: Integer := 5;

   task type philosopher is
      entry start(id1: Integer);
   end philosopher;

   task body philosopher is
      id: Integer := 0;
      second_fork: Integer := 0;
   begin
      accept start (id1 : in Integer) do
         id := id1;
      end start;
      second_fork := id rem 5 + 1;
      for i in 1..5 loop
         Put_Line("philosopher" & id'img & " thinking");
         can_take_fork.Seize;
         forks(id).Seize;
         Put_Line("Philosopher" & id'img & " await " & second_fork'img & " fork");
         forks(second_fork).Seize;
         Put_Line("Philosopher" & id'img & " eating " & i'img & " times");
         forks(id).Release;
         forks(second_fork).Release;
         can_take_fork.Release;
      end loop;
   end philosopher;

   philosophers: array(1..5) of philosopher;

begin
   for i in 1..5 loop
      philosophers(i).start(id1 => i);
   end loop;
end Main;
