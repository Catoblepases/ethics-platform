% -------------DOMAINS------------- %
time(0..6).
track(main(0..4);side(0..2)).
object(g1).
group(g1).
numberInGroup(g1,5).
bridgeOn().
buttonOn(main(0)).
buttonOn(side(1)).


posFluent(on(train,M)):-track(M).
posFluent(on(G,M)):-object(G),track(M).
posFluent(on(G,b)):-object(G).
posFluent(alive(G)):-group(G).
negative(neg(F)):-posFluent(F).
fluent(F):-posFluent(F).
fluent(F):-negative(F).
% -------------INITIAL SITUATION------------- %
initially(on(g1,main(3))).
initially(alive(g1)).
initially(on(train,main(0))).
% -------------SCENARIOS------------- %
sim(s0;s1).
performs(s1,switch(main(0)),0).
performs(s1,switch(side(1)),2).
% ------------ EVENT SPECIFICATION ------------------------------ %

% SWITCH
act(switch(M)):-buttonOn(M).
prec(on(train,M),switch(M)):-act(switch(M)).
effect(switch(M),neg(on(train,M))):-act(switch(M)).
effect(switch(main(N)),on(train,side(N))):-act(switch(main(N))).
effect(switch(side(N)),on(train,main(N))):-act(switch(side(N))).

% PUSH
act(push(O,B)):-object(O),bridgeOn(B,_),initially(on(O,B)).
prec(on(O,B),push(O,B)):-act(push(O,B)).
effect(push(O,B),neg(on(O,B))):-act(push(O,B)).
effect(push(O,B),on(O,M)):-act(push(O,B)), bridgeOn(B,M).

% Automatic action : run
auto(run(train,M)):-track(M).
prec(on(train,M),run(train,M)):-auto(run(train,M)).
effect(run(train,main(N-1)),on(train,main(N))):-auto(run(train,main(N))).
effect(run(train,side(N-1)),on(train,side(N))):-auto(run(train,side(N))).
effect(run(train,M),neg(on(train,M))):-auto(run(train,M)).

% Automatic action : crash
auto(crash(G,M)):-object(G),track(M).
prec(on(G,M),crash(G,M)):-auto(crash(G,M)).
prec(on(train,M),crash(G,M)):-auto(crash(G,M)).
effect(crash(G,M),neg(alive(G))):-auto(crash(G,M)).
effect(crash(G,M),neg(on(train,M))):-auto(crash(G,M)).

%Priorities
priority(crash(G,M),run(train,M)):-auto(crash(G,M)).
priority(A,run(train,M)):-act(A),track(M).


